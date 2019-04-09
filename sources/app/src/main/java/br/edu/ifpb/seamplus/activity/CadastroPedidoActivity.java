package br.edu.ifpb.seamplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class CadastroPedidoActivity extends AppCompatActivity {

    private ClienteRepository clienteRepository;
    private PedidoRepository pedidoRepository;
    private DividaRepository dividaRepository;
    private DescontoRepository descontoRepository;
    private ArrayList<Cliente> clientes;
    private Usuario usuario;
    private Intent intent;
    private Bundle dataBundle;

    private EditText etDescricaoCadastroPedido, etDataRealizacaoCadastroPedido, etValorCadastroPedido;
    private Spinner spinnerClientes;
    private CheckBox cbPedidoFinalizado, cbPedidoQuitado;
    private RadioButton rbUrgente, rbNormal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);

        intent = getIntent();
        dataBundle = intent.getExtras();
        usuario = (Usuario) dataBundle.getSerializable("usuarioLogado");

        pedidoRepository = new PedidoRepository(this);

        clienteRepository = new ClienteRepository(this);
        clientes = new ArrayList<>(clienteRepository.getAllByAtelieId(usuario.getAtelieId()));

        etDescricaoCadastroPedido = findViewById(R.id.etDescricaoCadastroPedido);
        etDataRealizacaoCadastroPedido = findViewById(R.id.etDataRealizacaoCadastroPedido);
        etDataRealizacaoCadastroPedido.addTextChangedListener(MaskEdit.mask(etDataRealizacaoCadastroPedido, MaskEdit.FORMAT_DATE));
        etValorCadastroPedido = findViewById(R.id.etValorCadastroPedido);
        cbPedidoFinalizado = findViewById(R.id.cbPedidoFinalizadoCadastroPedido);
        cbPedidoQuitado = findViewById(R.id.cbPedidoQuitadoCadastroPedido);
        rbUrgente = findViewById(R.id.rbUrgenteCadastroPedido);
        rbNormal = findViewById(R.id.rbNormalCadastroPedido);

        setarDadosSpinnerClientes();
    }

    public void onClickButtonCadastrarPedido(View view) {
        Date dataRealizacao = new Date();

        String planDescricaoCadastroPedido = etDescricaoCadastroPedido.getText().toString();
        String dataRealizacaoText = etDataRealizacaoCadastroPedido.getText().toString();
        String valorCadastroPedido = etValorCadastroPedido.getText().toString();
        double planValorCadastroPedido = Double.parseDouble(valorCadastroPedido);
        int positionSelected = spinnerClientes.getSelectedItemPosition();
        long clienteId = clientes.get(positionSelected).getId();

        boolean pedidoFinalizado = false;
        if(cbPedidoFinalizado.isChecked()) pedidoFinalizado = true;

        SituacaoPedido situacaoPedido = SituacaoPedido.NAO_QUITADO;
        if (cbPedidoQuitado.isChecked()) {
            situacaoPedido = SituacaoPedido.QUITADO;

            dividaRepository = new DividaRepository(this);
            descontoRepository = new DescontoRepository(this);

            Divida divida = dividaRepository.getByClienteId(clienteId);

            Desconto desconto = new Desconto(new Date(), planValorCadastroPedido, divida.getId());
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


        if (planDescricaoCadastroPedido.trim().length() > 0) {
            if (dataRealizacaoText.trim().length() > 0) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    dataRealizacao = sdf.parse(dataRealizacaoText);
                } catch (ParseException e) {
                    etDataRealizacaoCadastroPedido.setError("Campo data deve estar no formato dd/mm/aaaa");
                    Log.e("Exception", e.getMessage());
                }

                if (valorCadastroPedido.trim().length() > 0){

                    Pedido pedido = new Pedido(planDescricaoCadastroPedido, planValorCadastroPedido,
                            dataRealizacao, pedidoFinalizado, prioridadePedido, situacaoPedido, clienteId, usuario.getAtelieId());

                    pedidoRepository.save(pedido);

                    dataBundle.putSerializable("usuarioLogado", usuario);
                    intent.putExtras(dataBundle);
                    intent.setClass(getApplicationContext(), PedidosActivity.class);
                    Toast.makeText(getApplicationContext(), "Pedido Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
                    startActivity(intent);

                } else etValorCadastroPedido.setError("Campo está vazio!");
            } else etDataRealizacaoCadastroPedido.setError("Campo está vazio!");
        } else etDescricaoCadastroPedido.setError("Campo está vazio!");

    }


    public void setarDadosSpinnerClientes(){
        spinnerClientes = findViewById(R.id.spinnerClienteCadastroPedido);
        ArrayList<String> nomes = new ArrayList<>();
        for (Cliente c : clientes) nomes.add(c.getNome());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nomes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);

        MenuItem item4 = menu.findItem(R.id.item4);
        item4.setVisible(false);
        MenuItem item5 = menu.findItem(R.id.item5);
        item5.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.item1) {
            intent.setClass(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.item2) {
            intent.setClass(getApplicationContext(), ClientesActivity.class);
            startActivity(intent);
        } else if (id == R.id.item3) {
            intent.setClass(getApplicationContext(), PedidosActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
