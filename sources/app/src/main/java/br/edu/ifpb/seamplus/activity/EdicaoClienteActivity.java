package br.edu.ifpb.seamplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.enums.Sexo;
import br.edu.ifpb.seamplus.repository.ClienteRepository;

public class EdicaoClienteActivity extends AppCompatActivity {

    private ClienteRepository clienteRepository;
    private Intent intent;
    private Bundle dataBundle;
    private Cliente cliente;

    private EditText etNome, etApelido;
    private Spinner spinnerSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_cliente);

        etNome = findViewById(R.id.etNomeEdicaoCliente);
        etApelido = findViewById(R.id.etApelidoEdicaoCliente);
        spinnerSexo = findViewById(R.id.spinnerSexoEdicaoCliente);

        clienteRepository = new ClienteRepository(this);

        intent = getIntent();
        dataBundle = intent.getExtras();
        cliente = (Cliente) dataBundle.getSerializable("clienteParaEdicao");

        Log.d("cliente da edição", cliente.toString());

        etNome.setText(cliente.getNome());
        etApelido.setText(cliente.getApelido());
        setarDadosSpinnerSexo();

//        falta setar o sexo

    }

    public void onClickButtonConcluirEdicaoCliente(View view) {

        String planNomeCliente = etNome.getText().toString();
        String planApelidoCliente = etApelido.getText().toString();
        Sexo sexo = Sexo.valueOf(spinnerSexo.getSelectedItem().toString().toUpperCase());

        if (planNomeCliente.trim().length() > 0) {
            if (planApelidoCliente.trim().length() > 0){

                cliente.setNome(planNomeCliente);
                cliente.setApelido(planApelidoCliente);
                cliente.setSexo(sexo);

                clienteRepository.update(cliente);

                dataBundle.putLong("atelieId", cliente.getAtelieId());
                intent.putExtras(dataBundle);
                intent.setAction("edicaoCliente");
                intent.setClass(getApplicationContext(), ClientesActivity.class);
                Toast.makeText(this, "Cliente " + cliente.getNome().toUpperCase() + " editado!", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        } else etNome.setError("Campo está vazio!");
    }

    public void setarDadosSpinnerSexo(){
        spinnerSexo = findViewById(R.id.spinnerSexoEdicaoCliente);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSexo.setAdapter(adapter);
        spinnerSexo.setSelection(cliente.getSexo().ordinal());
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.popup_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if(id == R.id.item1) {
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//        } else if (id == R.id.item2) {
//            startActivity(new Intent(getApplicationContext(), ClientesActivity.class));
//        } else if (id == R.id.item3) {
//            startActivity(new Intent(getApplicationContext(), PedidosActivity.class));
//        } else {
//            startActivity(new Intent(getApplicationContext(), EdicaoUsuarioActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
