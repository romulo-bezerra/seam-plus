package br.edu.ifpb.seamplus.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Divida;
import br.edu.ifpb.seamplus.model.Usuario;
import br.edu.ifpb.seamplus.model.enums.Sexo;

public class CadastroClienteActivity extends AppCompatActivity {

    EditText etNomeCliente, etApelidoCliente;
    Spinner spSexoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        etNomeCliente = findViewById(R.id.etNomeClienteCadastro);
        etApelidoCliente = findViewById(R.id.etApelidoClienteCadastro);
        spSexoCliente = findViewById(R.id.spSexoClienteCadastro);
        setarDadosSpinnerSexo();
    }

    public void onClickButtonCadastrarCliente(View view) {
        Intent intent = getIntent();
        Bundle dataBundle = new Bundle();

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
                cliente.setDivida(divida);

                dataBundle.putSerializable("clienteParaCadastro", cliente);

                intent.setClass(getApplicationContext(), ClientesActivity.class);
                intent.putExtras(dataBundle);
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
