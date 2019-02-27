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
import br.edu.ifpb.seamplus.model.Usuario;

public class MainActivity extends AppCompatActivity {

    Button btnEntrar;
    EditText etEmail, etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEntrar = findViewById(R.id.btnEntrar);
        etEmail = findViewById(R.id.etEmailLogin);
        etSenha = findViewById(R.id.etSenhaLogin);
    }

    public void onClickButtonEntrar(View view){
        String planEmail = etEmail.getText().toString();
        String planSenha = etSenha.getText().toString();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Usuario usuario = (Usuario) bundle.getSerializable("usuarioCadastrado");

        if (planEmail.trim().length() > 0) {
            if (planSenha.length() > 0) {

                if (usuario.getEmail().equalsIgnoreCase(planEmail) && usuario.getSenha().equalsIgnoreCase(planSenha)){
                    Toast.makeText(getApplicationContext(), "Bem-Vindo, " + usuario.getNome(), Toast.LENGTH_LONG).show();

                    if(usuario.getAtelie() == null){
                        intent.setClass(getApplicationContext(), CadastroAtelieActivity.class);
                    } else intent.setClass(getApplicationContext(), HomeActivity.class);

                } else {
                    etEmail.setError("Email incorreto");
                    etSenha.setError("Senha incorreto");
                }

            } else etSenha.setError("Campo senha vazio!");
        } else etEmail.setError("Campo email vazio!");

        startActivity(intent);
    }

    public void nextTelaCadastro(View view) {
        Intent proximaTela = new Intent(MainActivity.this, CadastroUsuarioActivity.class);
        startActivity(proximaTela);
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
