package br.edu.ifpb.seamplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Divida;
import br.edu.ifpb.seamplus.model.Usuario;
import br.edu.ifpb.seamplus.model.enums.Sexo;
import br.edu.ifpb.seamplus.repository.ClienteRepository;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText etNomeCliente, etApelidoCliente;
    private Spinner spSexoCliente;
    private ClienteRepository clienteRepository;
    private Usuario usuario;
    private Intent intent;
    private Bundle dataBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        clienteRepository = new ClienteRepository(this);

        etNomeCliente = findViewById(R.id.etNomeClienteCadastro);
        etApelidoCliente = findViewById(R.id.etApelidoClienteCadastro);
        spSexoCliente = findViewById(R.id.spSexoClienteCadastro);
        setarDadosSpinnerSexo();

        intent = getIntent();
        dataBundle = intent.getExtras();
        usuario = (Usuario) dataBundle.getSerializable("usuarioLogado");

    }

    public void onClickButtonCadastrarCliente(View view) {


        String planNomeCliente = etNomeCliente.getText().toString();
        String planApelidoCliente = etApelidoCliente.getText().toString();
        Sexo sexo = Sexo.valueOf(spSexoCliente.getSelectedItem().toString().toUpperCase());

        if (planNomeCliente.trim().length() > 0) {
            if (planApelidoCliente.trim().length() > 0){

                Divida divida = new Divida();
                divida.setTotal(20);

                Cliente cliente = new Cliente();
                cliente.setNome(planNomeCliente);
                cliente.setApelido(planApelidoCliente);
                cliente.setSexo(sexo);
                cliente.setAtelieId(usuario.getAtelieId());

                clienteRepository.save(cliente);

//                dataBundle.putSerializable("clienteParaCadastro", cliente);
//                intent.putExtras(dataBundle);
                intent.setClass(getApplicationContext(), ClientesActivity.class);
                startActivity(intent);
            }
        } else etNomeCliente.setError("Campo está vazio!");
    }

    public void setarDadosSpinnerSexo(){
        Spinner spinnerSexo = findViewById(R.id.spSexoClienteCadastro);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);
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
