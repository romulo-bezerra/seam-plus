package br.edu.ifpb.seamplus.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Usuario;
import br.edu.ifpb.seamplus.repository.UsuarioRepository;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private UsuarioRepository usuarioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmailLogin);
        etSenha = findViewById(R.id.etSenhaLogin);

    }


    @Override
    public void onResume(){
        super.onResume();

        if (verificaConexao()){
            Toast.makeText(getApplicationContext(),
                    "Esse APP não necessita de internet. Desligue o WIFI ou Dados Móveis para poupar bateria.",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStart(){
        super.onStart();

        Intent intent = getIntent();
        Bundle dataBundle = new Bundle();

        usuarioRepository = new UsuarioRepository(this);

        Usuario usuario = usuarioRepository.getLogado();

        if(usuario != null){
            dataBundle.putSerializable("usuarioLogado", usuario);
            intent.putExtras(dataBundle);
            intent.setClass(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    }

    public boolean verificaConexao() {
        boolean conectado = false;
        ConnectivityManager conectivtyManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    public void onClickButtonEntrar(View view){
        String planEmail = etEmail.getText().toString();
        String planSenha = etSenha.getText().toString();

        Intent intent = getIntent();

        usuarioRepository = new UsuarioRepository(this);
        Usuario usuario = usuarioRepository.getByEmail(planEmail);

        Bundle dataBundle = new Bundle();

        if (planEmail.trim().length() > 0) {
            if (planSenha.length() > 0) {

                if (usuarioRepository.verifyExistentUser(planEmail) == 1){
                    if (usuario.getEmail().equalsIgnoreCase(planEmail) && usuario.getSenha().equalsIgnoreCase(planSenha)){
                        Toast.makeText(getApplicationContext(), "Bem-Vindo, " + usuario.getNome().toUpperCase(), Toast.LENGTH_LONG).show();

                        usuario.setLogado(true);
                        usuarioRepository.update(usuario);

                        dataBundle.putSerializable("usuarioLogado", usuario);
                        intent.putExtras(dataBundle);
                        intent.setClass(getApplicationContext(), HomeActivity.class);

                    } else {
                        etEmail.setError("Email incorreto");
                        etSenha.setError("Senha incorreto");
                    }

                }else {
                    etEmail.setError("E-mail não cadastrado!");
                    Toast.makeText(getApplicationContext(), "E-mail não cadastrado. Cadastre-se e tente novamente!", Toast.LENGTH_LONG).show();
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
    public void onBackPressed() {}

}