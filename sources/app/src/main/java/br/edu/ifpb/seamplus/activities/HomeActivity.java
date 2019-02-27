package br.edu.ifpb.seamplus.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.activities.adapters.LinhaScreenHomeAdapter;
import br.edu.ifpb.seamplus.model.Usuario;

public class HomeActivity extends AppCompatActivity {

    Intent intent;
    Bundle dataBundle;
    Usuario usuario;
    TextView tvAtelie;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        intent = getIntent();
        dataBundle = intent.getExtras();
        usuario = (Usuario) dataBundle.getSerializable("usuarioCadastrado");

        tvAtelie = findViewById(R.id.tvAtelie);
        tvAtelie.setText(usuario.getAtelie().getNomeFantasia());

        listView = findViewById(R.id.lvHome);
        final ArrayList<TempObject> tempObjects = popularArrayTempObjects();
        ArrayAdapter adapter = new LinhaScreenHomeAdapter(this, tempObjects);
        listView.setAdapter(adapter);
    }


    public void onClickButtonEditAtelie (View view) {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(HomeActivity.this);
        View viewAlertDialog = getLayoutInflater().inflate(R.layout.dialog_edit_atelie, null);
        final EditText etAtelieDialog = viewAlertDialog.findViewById(R.id.etAtelieDialog);
        Button btnEditAtelieDialog = viewAlertDialog.findViewById(R.id.btnEditAtelieDialog);

        final String nomeAtelieUsuario = usuario.getAtelie().getNomeFantasia();

        etAtelieDialog.setText(nomeAtelieUsuario);

        adBuilder.setView(viewAlertDialog);
        final AlertDialog alertDialog = adBuilder.create();
        alertDialog.show();

        btnEditAtelieDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeAtelieDialog = etAtelieDialog.getText().toString().trim();
                if (nomeAtelieUsuario.equalsIgnoreCase(nomeAtelieDialog)){
                    Toast.makeText(getApplicationContext(), "Atelie já tem esse nome!", Toast.LENGTH_LONG).show();
                    alertDialog.dismiss();
                } else {
                    if(!nomeAtelieDialog.isEmpty()){
                        tvAtelie.setText(nomeAtelieDialog);
                        Toast.makeText(getApplicationContext(), "Atelie editado com sucesso!", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    } else etAtelieDialog.setError("Campo não pode ser vazio!");
                }
            }
        });
    }

    private ArrayList<TempObject> popularArrayTempObjects() {
        ArrayList<TempObject> tempObjects = new ArrayList<TempObject>();

        TempObject tempObject1 = new TempObject(10, "Confeção Camisa", "João de Nalvinha");
        TempObject tempObject2 = new TempObject(2, "Fazer cortina", "Cormo de damião");
        TempObject tempObject3 = new TempObject(3, "Ajustar vestido casamento", "Maria Lurde");
        TempObject tempObject4 = new TempObject(4, "Cortar camisa", "Renan");
        TempObject tempObject5 = new TempObject(5, "Cortar short", "Moza");
        TempObject tempObject6 = new TempObject(6, "Fazer borno", "Fernando");
        tempObjects.add(tempObject1);
        tempObjects.add(tempObject2);
        tempObjects.add(tempObject3);
        tempObjects.add(tempObject4);
        tempObjects.add(tempObject5);
        tempObjects.add(tempObject6);

        return tempObjects;
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

