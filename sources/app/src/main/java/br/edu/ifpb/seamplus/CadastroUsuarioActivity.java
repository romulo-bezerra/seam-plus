package br.edu.ifpb.seamplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastroUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        setarDadosSpinnerSexo();

    }

    public void setarDadosSpinnerSexo(){
        Spinner spinnerSexo = findViewById(R.id.spinnerSexo);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerSexo.setAdapter(adapter);
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