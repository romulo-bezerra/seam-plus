package br.edu.ifpb.seamplus.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Divida;
import br.edu.ifpb.seamplus.model.Pedido;
import br.edu.ifpb.seamplus.model.enums.Sexo;
import br.edu.ifpb.seamplus.util.MaskEdit;

public class CadastroPedidoActivity extends AppCompatActivity {

    EditText etDescricaoCadastroPedido, etDataRealizacaoCadastroPedido, etValorCadastroPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);

        etDescricaoCadastroPedido = findViewById(R.id.etDescricaoCadastroPedido);
        etDataRealizacaoCadastroPedido = findViewById(R.id.etDataRealizacaoCadastroPedido);
        etDataRealizacaoCadastroPedido.addTextChangedListener(MaskEdit.mask(etDataRealizacaoCadastroPedido, MaskEdit.FORMAT_DATE));
        etValorCadastroPedido = findViewById(R.id.etValorCadastroPedido);
    }

    public void onClickButtonCadastrarPedido(View view) {
        Date dataRealizacao = new Date();

        String planDescricaoCadastroPedido = etDescricaoCadastroPedido.getText().toString();
        String dataRealizacaoText = etDataRealizacaoCadastroPedido.getText().toString();
        String planValorCadastroPedido = etValorCadastroPedido.getText().toString();

        if (planDescricaoCadastroPedido.trim().length() > 0) {
            if (dataRealizacaoText.trim().length() > 0) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    dataRealizacao = sdf.parse(dataRealizacaoText);
                } catch (ParseException e) {
                    etDataRealizacaoCadastroPedido.setError("Campo data deve estar no formato dd/mm/aaaa");
                    Log.e("Exception", e.getMessage());
                }

                if (planValorCadastroPedido.trim().length() > 0){

                    Intent intent = getIntent();
                    Bundle dataBundle = new Bundle();

                    Pedido pedido = new Pedido();
                    pedido.setDescricao(planDescricaoCadastroPedido);
                    pedido.setValor(Double.valueOf(planValorCadastroPedido));
                    pedido.setDataRealizacao(dataRealizacao);

                    Cliente cliente = new Cliente();
                    cliente.setNome("Arthur");
                    cliente.setSexo(Sexo.MASCULINO);

                    pedido.setCliente(cliente);

                    dataBundle.putSerializable("pedidoParaCadastro", pedido);

                    intent.setClass(getApplicationContext(), PedidosActivity.class);
                    intent.putExtras(dataBundle);
                    startActivity(intent);

                } else etValorCadastroPedido.setError("Campo está vazio!");
            } else etDataRealizacaoCadastroPedido.setError("Campo está vazio!");
        } else etDescricaoCadastroPedido.setError("Campo está vazio!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.item1) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        } else if (id == R.id.item2) {
            startActivity(new Intent(getApplicationContext(), ClientesActivity.class));
        } else if (id == R.id.item3) {
            startActivity(new Intent(getApplicationContext(), PedidosActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), EdicaoUsuarioActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
