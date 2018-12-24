package br.edu.ifpb.seamplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClientesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        ListView lista = findViewById(R.id.lvClientes);
        ArrayList<TempObject> tempObjects = popularArrayTempObjects();
        ArrayAdapter adapter = new LinhaScreenClientesAdapter(this, tempObjects);
        lista.setAdapter(adapter);

    }

    private ArrayList<TempObject> popularArrayTempObjects() {
        ArrayList<TempObject> tempObjects = new ArrayList<TempObject>();

        TempObject tempObject = new TempObject("Temp Object 01", "R$ 30,00");
        tempObjects.add(tempObject);
        tempObjects.add(tempObject);
        tempObjects.add(tempObject);
        tempObjects.add(tempObject);
        tempObjects.add(tempObject);
        tempObjects.add(tempObject);

        return tempObjects;
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
