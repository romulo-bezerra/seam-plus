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
import br.edu.ifpb.seamplus.activities.adapters.LinhaScreenClientesAdapter;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Divida;
import br.edu.ifpb.seamplus.model.enums.Sexo;

public class ClientesActivity extends AppCompatActivity {

    ArrayList<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        ListView lista = findViewById(R.id.lvClientes);
        clientes = popularArrayTempObjects();
        ArrayAdapter adapter = new LinhaScreenClientesAdapter(this, clientes);
        lista.setAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.btnAddCliente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.setClass(getApplicationContext(), CadastroClienteActivity.class);
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
            Cliente cliente = (Cliente) dataBundle.getSerializable("clienteParaCadastro");
            if (clientes.add(cliente)){
                Toast.makeText(getApplicationContext(), "Client added on list", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){ }


    }


    private ArrayList<Cliente> popularArrayTempObjects() {
        ArrayList<Cliente> clientes = new ArrayList<>();

        Divida divida = new Divida();
        divida.setId(1);
        divida.setTotal(30);

        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("João");
        cliente.setSexo(Sexo.MASCULINO);
        cliente.setDivida(divida);

        clientes.add(cliente);
        clientes.add(cliente);
        clientes.add(cliente);
        clientes.add(cliente);

        return clientes;
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
