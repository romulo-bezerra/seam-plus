package br.edu.ifpb.seamplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.activity.adapters.LinhaScreenPedidosAdapter;
import br.edu.ifpb.seamplus.model.Pedido;
import br.edu.ifpb.seamplus.model.Usuario;
import br.edu.ifpb.seamplus.repository.PedidoRepository;
import br.edu.ifpb.seamplus.repository.UsuarioRepository;

public class PedidosActivity extends AppCompatActivity {

    ArrayList<Pedido> pedidos = new ArrayList<>();
    private Bundle dataBundle;
    private Intent intent;
    private Usuario usuario;
    private PedidoRepository pedidoRepository;
    private UsuarioRepository usuarioRepository;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        pedidoRepository = new PedidoRepository(this);

        intent = getIntent();
        dataBundle = intent.getExtras();
        usuario = (Usuario) dataBundle.getSerializable("usuarioLogado");

//        if (intent.getAction().equalsIgnoreCase("edicaoPedido")){
//            long atelieId = dataBundle.getLong("atelieId");
//            pedidos = new ArrayList<>(pedidoRepository.getAllByAtelieId(atelieId));
//        }else pedidos = new ArrayList<>(pedidoRepository.getAllByAtelieId(usuario.getAtelieId()));
//
        usuarioRepository = new UsuarioRepository(this);
        usuario = usuarioRepository.getLogado();

        pedidos = new ArrayList<>(pedidoRepository.getAllByAtelieId(usuario.getAtelieId()));

        if (!pedidos.isEmpty()){
            ListView lista = findViewById(R.id.lvPedidos);
            adapter = new LinhaScreenPedidosAdapter(this, pedidos);
            lista.setAdapter(adapter);
        }

        FloatingActionButton fab = findViewById(R.id.btnAddPedido);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBundle.putSerializable("usuarioLogado", usuario);
                intent.putExtras(dataBundle);
                intent.setClass(getApplicationContext(), CadastroPedidoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!pedidos.isEmpty()){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);

        MenuItem item3 = menu.findItem(R.id.item3);
        item3.setVisible(false);
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
        }
        return super.onOptionsItemSelected(item);
    }

}