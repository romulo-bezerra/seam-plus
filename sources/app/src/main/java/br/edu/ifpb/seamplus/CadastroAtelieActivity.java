package br.edu.ifpb.seamplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CadastroAtelieActivity extends AppCompatActivity {

    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atelie);

        btnCadastrar = findViewById(R.id.btnCadastrarAtelie);

        //Metodo temporario (à ser melhorado)
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent proximaTela = new Intent(CadastroAtelieActivity.this, HomeActivity.class);
                startActivity(proximaTela);

            }
        });

    }

    //Método Temporário (a ser melhorado)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return true;
    }

    //Método Temporário (a ser melhorado)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.item1) {

            Toast.makeText(getApplicationContext(), "Item 1", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.item2) {

            Toast.makeText(getApplicationContext(), "Item 2", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.item3) {

            Toast.makeText(getApplicationContext(), "Item 3", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Item 4", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
