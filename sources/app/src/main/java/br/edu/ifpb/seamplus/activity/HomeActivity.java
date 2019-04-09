package br.edu.ifpb.seamplus.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import br.edu.ifpb.seamplus.activity.adapters.LinhaScreenHomeAdapter;
import br.edu.ifpb.seamplus.model.Atelie;
import br.edu.ifpb.seamplus.model.Pedido;
import br.edu.ifpb.seamplus.model.Usuario;
import br.edu.ifpb.seamplus.repository.AtelieRepository;
import br.edu.ifpb.seamplus.repository.PedidoRepository;
import br.edu.ifpb.seamplus.repository.UsuarioRepository;

public class HomeActivity extends AppCompatActivity {

    ArrayList<Pedido> pedidos = new ArrayList<>();
    private Intent intent;
    private Bundle dataBundle;
    private Usuario usuario;
    private Atelie atelie;
    private TextView tvAtelie;
    private AtelieRepository atelieRepository;
    private PedidoRepository pedidoRepository;
    private UsuarioRepository usuarioRepository;
    ArrayAdapter adapter;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pedidoRepository = new PedidoRepository(this);

        intent = getIntent();
        dataBundle = intent.getExtras();
        usuario = (Usuario) dataBundle.getSerializable("usuarioLogado");

        usuarioRepository = new UsuarioRepository(this);
        usuario = usuarioRepository.getLogado();

        atelieRepository = new AtelieRepository(this);
        atelie = atelieRepository.getAtelieById(usuario.getAtelieId());

        pedidos = new ArrayList<>(pedidoRepository.getAllPedidosNaoFinalizados(atelie.getId()));

        Log.d("Atelie tela home", atelie.toString());

        tvAtelie = findViewById(R.id.tvAtelie);
        tvAtelie.setText(atelie.getNomeFantasia());

        if (!pedidos.isEmpty()){
            listView = findViewById(R.id.lvHome);
            adapter = new LinhaScreenHomeAdapter(this, pedidos);
            listView.setAdapter(adapter);
        }
    }

    public void onClickButtonEditAtelie (View view) {
        AlertDialog.Builder adBuilder = new AlertDialog.Builder(HomeActivity.this);
        View viewAlertDialog = getLayoutInflater().inflate(R.layout.dialog_edit_atelie, null);
        final EditText etAtelieDialog = viewAlertDialog.findViewById(R.id.etAtelieDialog);
        Button btnEditAtelieDialog = viewAlertDialog.findViewById(R.id.btnEditAtelieDialog);

        final String nomeAtelieUsuario = atelie.getNomeFantasia();

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
                        atelie.setNomeFantasia(nomeAtelieDialog);
                        atelieRepository.update(atelie);
                        Toast.makeText(getApplicationContext(), "Atelie editado com sucesso!", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                    } else etAtelieDialog.setError("Campo não pode ser vazio!");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);

        MenuItem item = menu.findItem(R.id.item1);
        item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item2) {
            intent.setClass(getApplicationContext(), ClientesActivity.class);
            startActivity(intent);
        } else if (id == R.id.item3) {
            intent.setClass(getApplicationContext(), PedidosActivity.class);
            startActivity(intent);
        } else if (id == R.id.item5){
            dataBundle.remove("usuarioLogado");
            usuarioRepository = new UsuarioRepository(this);
            usuario.setLogado(false);
            usuarioRepository.update(usuario);
            Toast.makeText(getApplicationContext(), "Logout success!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (id == R.id.item4){
            intent.setClass(getApplicationContext(), EdicaoUsuarioActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}

