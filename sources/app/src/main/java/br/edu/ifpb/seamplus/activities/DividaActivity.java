package br.edu.ifpb.seamplus.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.services.DescontoPagamentoService;
import br.edu.ifpb.seamplus.util.NotificationUtil;

public class DividaActivity extends AppCompatActivity {

    TextView tvValorDivida;
    EditText etValorDescontoPagamento;

    Cliente cliente;

    Intent intent;
    Bundle bundle;

    private BroadcastReceiver brPullFeed = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();

            Cliente cliente = (Cliente) bundle.getSerializable("clienteDescontado");
            double valorDesconto = bundle.getDouble("valorDesconto");

            Log.d("Desconto broadasct:", String.valueOf(valorDesconto));
            Log.d("Cliente Broadcast:", cliente.getNome());

            TextView textView = findViewById(R.id.tvValorDivida);
            textView.setText(String.valueOf(cliente.getDivida().getTotal()));

            if(cliente.getDivida().getTotal() == 0){
                NotificationUtil.create(context, 1, intent, R.mipmap.ic_launcher, "Owwh! Show!!!", cliente.getNome() + "está com a dívida zerada!");
            } else{
                NotificationUtil.create(context, 1, intent, R.mipmap.ic_launcher, "Desconto Realizado", "Desconto de " + valorDesconto + " aplicado!");
            }

            Toast.makeText(context, "Desconto realizado com sucesso!", Toast.LENGTH_LONG).show();

            //VOLTAR PARA A TELA DE CLIENTES COM A DIVIDA ATUALIZADA

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divida);

        tvValorDivida = findViewById(R.id.tvValorDivida);
        etValorDescontoPagamento = findViewById(R.id.etValorDescontoPagamento);
    }

    @Override
    public void onResume(){
        super.onResume();

        intent = getIntent();
        bundle = intent.getExtras();
        cliente = (Cliente) bundle.getSerializable("clienteParaDivida");

        tvValorDivida.setText(String.valueOf(cliente.getDivida().getTotal()));

        LocalBroadcastManager.getInstance(this).registerReceiver(brPullFeed, new IntentFilter("DESCONTAR_PAGAMENTO"));

    }

    public void onClickButtonDescontarPagamento(View view){

        double planValorDescontoPagamento = Double.valueOf(etValorDescontoPagamento.getText().toString());

        Log.d("Valor desconto DA:", String.valueOf(planValorDescontoPagamento));
        Log.d("Cliente DA:", cliente.getNome());
        Log.d("Divida Cliente DA:", ""+cliente.getDivida().getTotal());

        bundle.putDouble("valorDesconto", planValorDescontoPagamento);

        bundle.putSerializable("clienteParaDivida", cliente);

        intent = new Intent(this, DescontoPagamentoService.class);
        intent.setAction("DESCONTAR_PAGAMENTO");

        intent.putExtras(bundle);

        startService(intent);

    }

    @Override
    public void onStop(){
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(brPullFeed);
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
