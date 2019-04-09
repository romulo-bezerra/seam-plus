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
import br.edu.ifpb.seamplus.activity.adapters.LinhaScreenClientesAdapter;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Usuario;
import br.edu.ifpb.seamplus.repository.ClienteRepository;
import br.edu.ifpb.seamplus.repository.UsuarioRepository;

public class ClientesActivity extends AppCompatActivity {

    ArrayList<Cliente> clientes = new ArrayList<>();
    private ClienteRepository clienteRepository;
    private UsuarioRepository usuarioRepository;
    private Intent intent;
    private Bundle dataBundle;
    private Usuario usuario;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        intent = getIntent();
        dataBundle = intent.getExtras();
        usuario = (Usuario) dataBundle.getSerializable("usuarioLogado");

//        Cliente cliente = (Cliente) dataBundle.getSerializable("clienteToView");
//        Log.d("cliente do adapter", cliente.toString());
 
        clienteRepository = new ClienteRepository(this);

        ListView lista = findViewById(R.id.lvClientes);
//        if (intent.getAction().equalsIgnoreCase("edicaoCliente")){
//            long atelieId = dataBundle.getLong("atelieId");
////            clientes.clear();
//            clientes = new ArrayList<>(clienteRepository.getAllByAtelieId(atelieId));
//
//        }else{
//            clientes = new ArrayList<>(clienteRepository.getAllByAtelieId(usuario.getAtelieId()));
//        }
        usuarioRepository = new UsuarioRepository(this);
        usuario = usuarioRepository.getLogado();
        clientes = new ArrayList<>(clienteRepository.getAllByAtelieId(usuario.getAtelieId()));

        adapter = new LinhaScreenClientesAdapter(this, clientes);
        lista.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.btnAddCliente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBundle.putSerializable("usuarioLogado", usuario);
                intent.putExtras(dataBundle);
                intent.setClass(getApplicationContext(), CadastroClienteActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!clientes.isEmpty()){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.popup_menu, menu);

            MenuItem item2 = menu.findItem(R.id.item2);
            item2.setVisible(false);
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
            } else if (id == R.id.item3) {
                intent.setClass(getApplicationContext(), PedidosActivity.class);
                startActivity(intent);
            }
            return super.onOptionsItemSelected(item);
        }
}
