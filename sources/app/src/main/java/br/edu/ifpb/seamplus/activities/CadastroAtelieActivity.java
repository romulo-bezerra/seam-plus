package br.edu.ifpb.seamplus.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Atelie;
import br.edu.ifpb.seamplus.model.Usuario;

public class CadastroAtelieActivity extends AppCompatActivity {

    Button btnCadastrar;
    EditText etNomeAtelie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atelie);

        btnCadastrar = findViewById(R.id.btnCadastrarAtelie);
        etNomeAtelie = findViewById(R.id.etNomeAtelie);
    }


    public void onClickButtonCadastrarAtelie(View view){
        Atelie atelie = new Atelie();
        Usuario usuario;

        String planNomeAtelie = etNomeAtelie.getText().toString();

        Intent intent = getIntent();
        Bundle dataBundle = intent.getExtras();

        if (planNomeAtelie.trim().length() > 0) {
            atelie.setNomeFantasia(planNomeAtelie);

            usuario = (Usuario) dataBundle.getSerializable("usuarioCadastrado");
            usuario.setAtelie(atelie);

            dataBundle.putSerializable("usuarioCadastrado", usuario);

            intent.setClass(getApplicationContext(), HomeActivity.class);
            intent.putExtras(dataBundle);

            Toast.makeText(getApplicationContext(), "Ateliê Cadastrado! UA=" + usuario.getAtelie().getNomeFantasia(), Toast.LENGTH_LONG).show();
            startActivity(intent);
        } else etNomeAtelie.setError("Campo Vazio!");

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
