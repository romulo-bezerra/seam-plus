package br.edu.ifpb.seamplus.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.androidservice.DescontoPagamentoService;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Desconto;
import br.edu.ifpb.seamplus.model.Divida;
import br.edu.ifpb.seamplus.repository.DescontoRepository;
import br.edu.ifpb.seamplus.repository.DividaRepository;
import br.edu.ifpb.seamplus.util.NotificationUtil;

public class DividaActivity extends AppCompatActivity {

    private TextView tvValorDividaTelaDivida, tvClienteTelaDivida;
    private EditText etValorDescontoPagamentoTelaDivida;

    Cliente cliente;
    Divida divida;

    Intent intent;
    Bundle bundle;

    private DividaRepository dividaRepository;
    private DescontoRepository descontoRepository;

    private BroadcastReceiver brPullFeed = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            List<Desconto> descontosDaDivida = descontoRepository.getAllByDividaId(divida.getId());

            double descontoTotal = 0;

            if (!descontosDaDivida.isEmpty()){
                for (Desconto desconto : descontosDaDivida){
                    descontoTotal += desconto.getValor();
                }
            }


            Bundle bundle = intent.getExtras();
            tvValorDividaTelaDivida = findViewById(R.id.tvValorDividaTelaDivida);

            boolean valorExcedeDivida = bundle.getBoolean("valorExcedeDivida");

            if (valorExcedeDivida){
                NotificationUtil.create(context, 1, intent, R.mipmap.ic_launcher, "Impossível o Desconto", "Desconto é maior que a dívida!");
                Toast.makeText(context, "Falha: O desconto é maior que a dívida!", Toast.LENGTH_LONG).show();
                etValorDescontoPagamentoTelaDivida.setError("Insira um valor menor que a dívida");
            } else {
                double valorDesconto = bundle.getDouble("valorDesconto");
                NotificationUtil.create(context, 1, intent, R.mipmap.ic_launcher, "Desconto Realizado", "Desconto de R$ " + valorDesconto + " aplicado!");
                Toast.makeText(context, "Desconto realizado com sucesso!", Toast.LENGTH_LONG).show();
            }
            tvValorDividaTelaDivida.setText("R$ " + (divida.getTotal() - descontoTotal));

            if((divida.getTotal() - descontoTotal) == 0){
                NotificationUtil.create(context, 1, intent, R.mipmap.ic_launcher, "Owwh! Show!!!", cliente.getNome().toUpperCase() + " está com a dívida zerada!");
                Toast.makeText(context, "Dívida liquidada!", Toast.LENGTH_LONG).show();
            }

            //VOLTAR PARA A TELA DE CLIENTES COM A DIVIDA ATUALIZADA

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divida);

        dividaRepository = new DividaRepository(this);
        descontoRepository = new DescontoRepository(this);

        tvValorDividaTelaDivida = findViewById(R.id.tvValorDividaTelaDivida);
        tvClienteTelaDivida = findViewById(R.id.tvNomeClienteDividaTelaDivida);
        etValorDescontoPagamentoTelaDivida = findViewById(R.id.etValorDescontoPagamentoTelaDivida);

        intent = getIntent();
        bundle = intent.getExtras();
        cliente = (Cliente) bundle.getSerializable("clienteParaDivida");
        divida = (Divida) bundle.getSerializable("divida");

        String nomeCliente = !cliente.getApelido().isEmpty() ? cliente.getNome() + " (" + cliente.getApelido() + ")" : cliente.getNome();
        tvClienteTelaDivida.setText(nomeCliente);

        List<Desconto> descontosDaDivida = descontoRepository.getAllByDividaId(divida.getId());

        double descontoTotal = 0;

        if (!descontosDaDivida.isEmpty()){
            for (Desconto desconto : descontosDaDivida){
                descontoTotal += desconto.getValor();
            }
        }

        tvValorDividaTelaDivida.setText("R$ " + (divida.getTotal() - descontoTotal));

    }

    @Override
    public void onResume(){
        super.onResume();


//        tvValorDivida.setText(String.valueOf(cliente.getDivida().getTotal()));

        LocalBroadcastManager.getInstance(this).registerReceiver(brPullFeed, new IntentFilter("DESCONTAR_PAGAMENTO"));

    }

    public void onClickButtonDescontarPagamento(View view){

        double planValorDescontoPagamento = Double.valueOf(etValorDescontoPagamentoTelaDivida.getText().toString());

//        Log.d("Valor desconto DA:", String.valueOf(planValorDescontoPagamento));
//        Log.d("Cliente DA:", cliente.getNome());
//        Log.d("Divida Cliente DA:", ""+cliente.getDivida().getTotal());

        bundle.putDouble("valorDesconto", planValorDescontoPagamento);

        bundle.putSerializable("dividaParaServico", divida);

        intent = new Intent(this, DescontoPagamentoService.class);
        intent.setAction("DESCONTAR_PAGAMENTO");

        intent.putExtras(bundle);

        startService(intent);

    }

    public void onClickButtonDeletarDivida(View view){
        Desconto desconto = new Desconto(new Date(), divida.getTotal(), divida.getId());
        descontoRepository.save(desconto);

        divida.setQuitada(true);
        dividaRepository.update(divida);
        tvValorDividaTelaDivida.setText("R$ " + 0.0);
    }

    @Override
    public void onStop(){
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(brPullFeed);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.popup_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if(id == R.id.item1) {
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//        } else if (id == R.id.item2) {
//            startActivity(new Intent(getApplicationContext(), ClientesActivity.class));
//        } else if (id == R.id.item3) {
//            startActivity(new Intent(getApplicationContext(), PedidosActivity.class));
//        } else {
//            startActivity(new Intent(getApplicationContext(), EdicaoUsuarioActivity.class));
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
