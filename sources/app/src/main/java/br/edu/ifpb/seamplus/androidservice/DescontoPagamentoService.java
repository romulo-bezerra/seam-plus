package br.edu.ifpb.seamplus.androidservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Date;
import java.util.List;

import br.edu.ifpb.seamplus.model.Desconto;
import br.edu.ifpb.seamplus.model.Divida;
import br.edu.ifpb.seamplus.repository.DescontoRepository;

public class DescontoPagamentoService extends IntentService {

    public DescontoPagamentoService() {
        super("DESCONTAR_PAGAMENTO");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DescontoRepository descontoRepository = new DescontoRepository(this);

        Bundle bundle = intent.getExtras();

        Divida divida = (Divida) bundle.getSerializable("dividaParaServico");
        double valorDesconto = bundle.getDouble("valorDesconto");

        List<Desconto> descontosDaDivida = descontoRepository.getAllByDividaId(divida.getId());
        double valorDividaComDesconto = divida.calcularDividaComDescontos(descontosDaDivida);

        if ((valorDividaComDesconto - valorDesconto) >= 0){
            Desconto desconto = new Desconto(new Date(), valorDesconto, divida.getId());
            descontoRepository.save(desconto);
            bundle.putBoolean("valorExcedeDivida", false);
        }else{
            bundle.putBoolean("valorExcedeDivida", true);
        }

        bundle.putDouble("valorDescontado", valorDesconto);

//        Log.d("Valor desconto Service:", String.valueOf(valorDesconto));
//        Log.d("Cliente Service:", cliente.getNome());

//        double divida = cliente.getDivida().getTotal();
//        cliente.getDivida().setTotal(divida-valorDesconto);

//        bundle.remove("clienteParaDivida");
//        bundle.remove("clienteParaCadastro");
//        bundle.putSerializable("clienteDescontado", cliente);

        intent.putExtras(bundle);

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        manager.sendBroadcast(intent);
    }

}