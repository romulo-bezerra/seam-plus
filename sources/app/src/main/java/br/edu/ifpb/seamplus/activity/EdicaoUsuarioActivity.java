package br.edu.ifpb.seamplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import br.edu.ifpb.seamplus.repository.UsuarioRepository;
import br.edu.ifpb.seamplus.util.MaskEdit;

public class EdicaoUsuarioActivity extends AppCompatActivity {

    private Intent intent;
    private Bundle dataBundle;
    private Usuario usuario;
    private EditText etNome, etNascimento, etEmail, etSenha, etRepeteSenha;
    private Spinner spSexo;

    private UsuarioRepository usuarioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_usuario);

        intent = getIntent();
        dataBundle = intent.getExtras();
        usuario = (Usuario) dataBundle.getSerializable("usuarioLogado");

        etNome = findViewById(R.id.etNomeEdicaoUsuario);
        etNascimento = findViewById(R.id.etNascimentoEdicaoUsuario);
        etNascimento.addTextChangedListener(MaskEdit.mask(etNascimento, MaskEdit.FORMAT_DATE));
        spSexo = findViewById(R.id.spinnerSexoEdicaoUsuario);
        etEmail = findViewById(R.id.etEmailEdicaoUsuario);
        etSenha = findViewById(R.id.etSenhaEdicaoUsuario);
        etRepeteSenha = findViewById(R.id.etRepeteSenhaEdicaoUsuario);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        etNascimento.setText(sdf.format (usuario.getNascimento()));

        setarDadosSpinnerSexo();
        spSexo.setSelection(usuario.getSexo().ordinal());
        etNome.setText(usuario.getNome());
        etEmail.setText(usuario.getEmail());
        etSenha.setText(usuario.getSenha());
        etRepeteSenha.setText(usuario.getSenha());

        usuarioRepository = new UsuarioRepository(this);
    }

//    public String formatterDate(Date date){
//        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
//        try {
//            Date dateToFormatter = formatter.parse(date.toString());
//            return new SimpleDateFormat("dd-MM-yyyy").format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public Date parseTextToDate(String dataText){
//        String nascimentoText = dataText;
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            return sdf.parse(nascimentoText);
//        } catch (ParseException e) {
//            Log.e("Exception", e.getMessage());
//            Log.e("Excepetion Data", "Impossível de formatar data!");
//            return null;
//        }
//    }
//            etNascimento.setError("Campo data deve estar no formato dd/mm/aaaa");

    public void onClickButtonEditUsuario(View view) {
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
                            usuario.setNome(planNome);
                            usuario.setNascimento(planNascimento);
                            usuario.setEmail(planEmail);
                            usuario.setSenha(planSenha);
                            usuario.setSexo(sexo);

                            usuarioRepository.update(usuario);

                            final String alertSuccessRegister = "Usuário Editado com Sucesso!";
                            Toast.makeText(getApplicationContext(), alertSuccessRegister, Toast.LENGTH_LONG).show();

                            dataBundle.putSerializable("usuarioLogado", usuario);
                            intent.putExtras(dataBundle);
                            intent.setClass(this, HomeActivity.class);
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

    public void onClickButtonApagarConta(View view) {
        usuarioRepository.delete(usuario);
        Toast.makeText(getApplicationContext(), "Conta deletada com sucesso!", Toast.LENGTH_LONG).show();
        intent.setClass(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    private boolean equalPasswordAndRepeatedPassword(){
        String password = etSenha.getText().toString();
        String repeatedPassword = etRepeteSenha.getText().toString();
        return  password.equalsIgnoreCase(repeatedPassword);
    }

    public void setarDadosSpinnerSexo(){
        spSexo = findViewById(R.id.spinnerSexoEdicaoUsuario);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(adapter);
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
