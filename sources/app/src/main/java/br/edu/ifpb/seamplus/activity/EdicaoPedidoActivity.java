package br.edu.ifpb.seamplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Desconto;
import br.edu.ifpb.seamplus.model.Divida;
import br.edu.ifpb.seamplus.model.Pedido;
import br.edu.ifpb.seamplus.model.Usuario;
import br.edu.ifpb.seamplus.model.enums.Prioridade;
import br.edu.ifpb.seamplus.model.enums.SituacaoPedido;
import br.edu.ifpb.seamplus.repository.ClienteRepository;
import br.edu.ifpb.seamplus.repository.DescontoRepository;
import br.edu.ifpb.seamplus.repository.DividaRepository;
import br.edu.ifpb.seamplus.repository.PedidoRepository;
import br.edu.ifpb.seamplus.util.MaskEdit;

public class EdicaoPedidoActivity extends AppCompatActivity {

    private ClienteRepository clienteRepository;
    private PedidoRepository pedidoRepository;
    private DividaRepository dividaRepository;
    private DescontoRepository descontoRepository;
    private Intent intent;
    private Bundle dataBundle;
    private Pedido pedido;
    private ArrayList<Cliente> clientes;
    private Cliente cliente;
    private Usuario usuario;

    EditText etDescricao, etDataRealizacao, etValor;
    Spinner spinnerClientes;
    CheckBox cbPedidoFinalizado, cbPedidoQuitado;
    RadioButton rbUrgente, rbNormal;
    Button btnEditarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_pedido);

        pedidoRepository = new PedidoRepository(this);
        clienteRepository = new ClienteRepository(this);

        intent = getIntent();
        dataBundle = intent.getExtras();
        pedido = (Pedido) dataBundle.getSerializable("pedidoParaEdicao");
        usuario = (Usuario) dataBundle.getSerializable("usuarioLogado");

        clientes = new ArrayList<>(clienteRepository.getAllByAtelieId(pedido.getAtelieId()));

        etDescricao = findViewById(R.id.etDescricaoEdicaoPedido);
        etDataRealizacao = findViewById(R.id.etDataRealizacaoEdicaoPedido);
        etDataRealizacao.addTextChangedListener(MaskEdit.mask(etDataRealizacao, MaskEdit.FORMAT_DATE));
        etValor = findViewById(R.id.etValorEdicaoPedido);
        cbPedidoFinalizado = findViewById(R.id.cbPedidoFinalizadoEdicaoPedido);
        cbPedidoQuitado = findViewById(R.id.cbPedidoQuitadoEdicaoPedido);
        rbUrgente = findViewById(R.id.rbUrgenteEdicaoPedido);
        rbNormal = findViewById(R.id.rbNormalEdicaoPedido);
        btnEditarPedido = findViewById(R.id.btnConcluidoEdicaoPedido);

        setarDadosSpinnerClientes();
        cliente = clienteRepository.getById(pedido.getClienteId());
        spinnerClientes.setSelection(clientes.indexOf(cliente));

        etDescricao.setText(pedido.getDescricao());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        etDataRealizacao.setText(sdf.format (pedido.getDataRealizacao()));
        etValor.setText(""+pedido.getValor());

        if (pedido.isFinalizado()) cbPedidoFinalizado.setChecked(true);
        else cbPedidoFinalizado.setChecked(false);

        if (pedido.getPrioridade() == Prioridade.URGENTE) rbUrgente.setChecked(true);
        else rbNormal.setChecked(true);

        if (pedido.getSituacaoPedido() == SituacaoPedido.QUITADO) cbPedidoQuitado.setChecked(true);
        else cbPedidoQuitado.setChecked(false);


        btnEditarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               editacaoPedido();
            }
        });

    }


    public void editacaoPedido() {
        Date dataRealizacao = new Date();

        String planDescricao = etDescricao.getText().toString();
        String dataRealizacaoText = etDataRealizacao.getText().toString();
        String valorCadastroPedido = etValor.getText().toString();
        double planValorCadastroPedido = Double.parseDouble(valorCadastroPedido);
        int positionSelected = spinnerClientes.getSelectedItemPosition();
        long clienteId = clientes.get(positionSelected).getId();

        boolean pedidoFinalizado = false;
        if(cbPedidoFinalizado.isChecked()) pedidoFinalizado = true;

        SituacaoPedido situacaoPedido = SituacaoPedido.NAO_QUITADO;
        dividaRepository = new DividaRepository(this);
        descontoRepository = new DescontoRepository(this);
        Divida divida = dividaRepository.getByClienteId(clienteId);
        Desconto desconto;

        if (cbPedidoQuitado.isChecked() && pedido.getSituacaoPedido() == SituacaoPedido.QUITADO){
            situacaoPedido = SituacaoPedido.QUITADO;
        }

        if (cbPedidoQuitado.isChecked() && pedido.getSituacaoPedido() == SituacaoPedido.NAO_QUITADO) {
            situacaoPedido = SituacaoPedido.QUITADO;

            if(pedido.getValor() > divida.getTotal()){
                desconto = new Desconto(new Date(), divida.getTotal(), divida.getId());
            } else{
                desconto = new Desconto(new Date(), pedido.getValor(), divida.getId());
            }

            descontoRepository.save(desconto);
        }

        if (pedido.getSituacaoPedido() == SituacaoPedido.QUITADO && !cbPedidoQuitado.isChecked()){
            situacaoPedido = SituacaoPedido.NAO_QUITADO;
            desconto = new Desconto(new Date(), (pedido.getValor()*(-1)), divida.getId());
            descontoRepository.save(desconto);
        }


        Prioridade prioridadePedido = Prioridade.NORMAL;
        if(rbUrgente.isChecked()) {
            Log.d("urgente", "checado");
            prioridadePedido = Prioridade.URGENTE;
            rbNormal.setChecked(false);
        }
        if(rbNormal.isChecked()) {
            prioridadePedido = Prioridade.NORMAL;
            rbUrgente.setChecked(false);
        }


        if (planDescricao.trim().length() > 0) {
            if (dataRealizacaoText.trim().length() > 0) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    dataRealizacao = sdf.parse(dataRealizacaoText);
                } catch (ParseException e) {
                    etDataRealizacao.setError("Campo data deve estar no formato dd/mm/aaaa");
                    Log.e("Exception", e.getMessage());
                }

                if (valorCadastroPedido.trim().length() > 0){

                    pedido.setDescricao(planDescricao);
                    pedido.setValor(planValorCadastroPedido);
                    pedido.setDataRealizacao(dataRealizacao);
                    pedido.setFinalizado(pedidoFinalizado);
                    pedido.setPrioridade(prioridadePedido);
                    pedido.setSituacaoPedido(situacaoPedido);
                    pedido.setClienteId(clienteId);

                    pedidoRepository.update(pedido);

                    dataBundle.putLong("atelieId", pedido.getAtelieId());
                    intent.putExtras(dataBundle);
                    intent.setClass(getApplicationContext(), PedidosActivity.class);
                    Toast.makeText(this, "Pedido " + pedido.getDescricao().toUpperCase() + " editado!", Toast.LENGTH_LONG).show();
                    startActivity(intent);

                } else etValor.setError("Campo está vazio!");
            } else etDataRealizacao.setError("Campo está vazio!");
        } else etDescricao.setError("Campo está vazio!");

    }

    public void setarDadosSpinnerClientes(){
        spinnerClientes = findViewById(R.id.spinnerClientesEdicaoPedido);
        ArrayList<String> nomes = new ArrayList<>();
        for (Cliente c : clientes) nomes.add(c.getNome());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nomes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(adapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.popup_menu, menu);
//
//        MenuItem item4 = menu.findItem(R.id.item4);
//        item4.setVisible(false);
//        MenuItem item5 = menu.findItem(R.id.item5);
//        item5.setVisible(false);
//
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if(id == R.id.item1) {
//            intent.setClass(getApplicationContext(), HomeActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.item2) {
//            intent.setClass(getApplicationContext(), ClientesActivity.class);
//            startActivity(intent);
//        } else if (id == R.id.item3) {
//            intent.setClass(getApplicationContext(), PedidosActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
