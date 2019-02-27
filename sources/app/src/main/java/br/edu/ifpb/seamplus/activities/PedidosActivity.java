package br.edu.ifpb.seamplus.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.activities.adapters.LinhaScreenPedidosAdapter;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Pedido;
import br.edu.ifpb.seamplus.model.enums.Prioridade;
import br.edu.ifpb.seamplus.model.enums.Sexo;

public class PedidosActivity extends AppCompatActivity {

    ArrayList<Pedido> pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        ListView lista = findViewById(R.id.lvPedidos);
        ArrayList<Pedido> pedidos = popularListaPedidos();
        ArrayAdapter adapter = new LinhaScreenPedidosAdapter(this, pedidos);
        lista.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.btnAddPedido);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.setClass(getApplicationContext(), CadastroPedidoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();

        Intent intent = getIntent();
        Bundle dataBundle = intent.getExtras();

        //Temp
        try{
            Pedido pedido = (Pedido) dataBundle.getSerializable("pedidoParaCadastro");
            if (pedidos.add(pedido)){
                Toast.makeText(getApplicationContext(), "Pedido added on list", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){

            Toast.makeText(getApplicationContext(), "Pedido não adicionado", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Pedido> popularListaPedidos() {
        pedidos = new ArrayList<>();

        Cliente cliente = new Cliente();
        cliente.setNome("Leonan");
        cliente.setSexo(Sexo.MASCULINO);

        Pedido pedido = new Pedido();
        pedido.setDescricao("Confecção Camisa");
        pedido.setPrioridade(Prioridade.URGENTE);
        pedido.setValor(50);
        pedido.setCliente(cliente);

        pedidos.add(pedido);
        pedidos.add(pedido);
        pedidos.add(pedido);
        pedidos.add(pedido);
        pedidos.add(pedido);

        return pedidos;
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
