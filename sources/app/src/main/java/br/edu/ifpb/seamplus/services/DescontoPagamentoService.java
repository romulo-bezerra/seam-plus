package br.edu.ifpb.seamplus.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import br.edu.ifpb.seamplus.model.Cliente;

public class DescontoPagamentoService extends IntentService {

    public DescontoPagamentoService() {
        super("DESCONTAR_PAGAMENTO");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle bundle = intent.getExtras();

        Cliente cliente = (Cliente) bundle.getSerializable("clienteParaDivida");
        double valorDesconto = bundle.getDouble("valorDesconto");

        Log.d("Valor desconto Service:", String.valueOf(valorDesconto));
        Log.d("Cliente Service:", cliente.getNome());

        double divida = cliente.getDivida().getTotal();
        cliente.getDivida().setTotal(divida-valorDesconto);

        bundle.putDouble("valorDescontado", valorDesconto);
        bundle.remove("clienteParaDivida");
        bundle.remove("clienteParaCadastro");
        bundle.putSerializable("clienteDescontado", cliente);

        intent.putExtras(bundle);

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.sendBroadcast(intent);
    }

}