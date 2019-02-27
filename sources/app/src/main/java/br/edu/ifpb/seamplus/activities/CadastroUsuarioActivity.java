package br.edu.ifpb.seamplus.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Usuario;
import br.edu.ifpb.seamplus.model.enums.Sexo;
import br.edu.ifpb.seamplus.util.MaskEdit;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private Usuario usuario;
    EditText etNome, etNascimento, etEmail, etSenha, etRepeteSenha;
    Spinner spSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        setarDadosSpinnerSexo();
        etNome = findViewById(R.id.etNomeUsuario);
        etNascimento = findViewById(R.id.etNascimentoUsuario);
        etNascimento.addTextChangedListener(MaskEdit.mask(etNascimento, MaskEdit.FORMAT_DATE));
        spSexo = findViewById(R.id.spinnerSexo);
        etEmail = findViewById(R.id.etEmailUsuario);
        etSenha = findViewById(R.id.etSenhaUsuario);
        etRepeteSenha = findViewById(R.id.etRepeteSenhaUsuario);
    }

    public void onClickButtonCadastroUsuario(View view) {
        Date planNascimento = new Date();

        String planNome = etNome.getText().toString();
        String planEmail = etEmail.getText().toString();
        String planSenha = etSenha.getText().toString();
        String nascimentoText = etNascimento.getText().toString();
        Sexo sexo = Sexo.valueOf(spSexo.getSelectedItem().toString().toUpperCase());

        if (planNome.trim().length() > 0){
            if (nascimentoText.trim().length() > 0) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    planNascimento = sdf.parse(nascimentoText);
                } catch (ParseException e) {
                    etNascimento.setError("Campo data deve estar no formato dd/mm/aaaa");
                    Log.e("Exception", e.getMessage());
                }
                if (planEmail.trim().length() > 0){
                    if (planSenha.length() > 0) {
                        if (equalPasswordAndRepeatedPassword()) {
                            usuario = new Usuario(planNome, planNascimento, planEmail, planSenha, sexo);
                            final String alertSuccessRegister = "Usuário Cadastrado com Sucesso!";
                            Toast.makeText(getApplicationContext(), alertSuccessRegister, Toast.LENGTH_LONG).show();

                            Bundle dataBundle = new Bundle();
                            dataBundle.putSerializable("usuarioCadastrado", usuario);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setAction("CadastroUsuario");
                            intent.putExtras(dataBundle);
                            startActivity(intent);
                        } else {
                            etSenha.setError("As senhas não correspondem");
                            etRepeteSenha.setError("As senhas não correspondem");
                        }
                    } else etSenha.setError("Campo senha vazio!");
                } else etEmail.setError("Campo email vazio!");
            } else etNascimento.setError("Campo data vazio");
        } else etNome.setError("Campo nome vazio!");

    }

    private boolean equalPasswordAndRepeatedPassword(){
        String password = etSenha.getText().toString();
        String repeatedPassword = etRepeteSenha.getText().toString();
        return  password.equalsIgnoreCase(repeatedPassword);
    }

    public void setarDadosSpinnerSexo(){
        spSexo = findViewById(R.id.spinnerSexo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adapter);
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