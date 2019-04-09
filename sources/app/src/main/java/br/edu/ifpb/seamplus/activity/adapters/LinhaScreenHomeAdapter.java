package br.edu.ifpb.seamplus.activity.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Pedido;
import br.edu.ifpb.seamplus.model.enums.Prioridade;
import br.edu.ifpb.seamplus.repository.ClienteRepository;
import br.edu.ifpb.seamplus.repository.PedidoRepository;

public class LinhaScreenHomeAdapter extends ArrayAdapter<Pedido> implements View.OnClickListener {

    Context context;

    private int ultimaPosicao = -1;

    AlertDialog.Builder builder;
    AlertDialog alert;

    ArrayList<Pedido> dados;
    Pedido pedido;

    private PedidoRepository pedidoRepository;
    private ClienteRepository clienteRepository;

    private static class ViewHolder {

        TextView tvPedidoHome;
        Button btnClienteHome;
        TextView tvPrioridade;

        Button btnFinalizarPedido;
        Button btnVisualizarPedidoHome;
    }

    public LinhaScreenHomeAdapter(Context context, ArrayList<Pedido> dataSet) {
        super(context, R.layout.linha_screen_home, dataSet);

        this.dados = dataSet;

        this.context = context;
    }

//    public void updateDataSet(ArrayList<TempObject> dataSetUpdated) {
//        this.dados.clear();;
//        this.dados.addAll(dataSetUpdated);
//        notifyDataSetChanged();
//    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer){
        super.registerDataSetObserver(observer);
    }

    @Override
    public void onClick(View view) {

        int posicao = (Integer) view.getTag();

        Object object = getItem(posicao);

        pedido = (Pedido) object;

        switch (view.getId()) {

            case R.id.btnFinalizarPedido:

                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Alerta");
                builder.setMessage("Deseja finalizar este pedido?");
                builder.setCancelable(true);
                builder.setIcon(R.mipmap.ic_launcher);

                builder.setPositiveButton("SIM", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dados.remove(pedido)) {
                            pedidoRepository = new PedidoRepository(context);
                            pedido.setFinalizado(true);
                            pedidoRepository.update(pedido);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Pedido " + pedido.getDescricao().toUpperCase() + " finalizado!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Não foi possível finalizar o pedido!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                builder.setNegativeButton("CANCELAR", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alert = builder.create();
                alert.show();

                break;


            case R.id.btnVisualizarPedidoHome:

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Snackbar.make(view, "Valor: " + pedido.getValor()
                        + "\n" + "Data: "+ sdf.format (pedido.getDataRealizacao()), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View dataSet, @NonNull ViewGroup parent) {

        pedido = getItem(position);

        ViewHolder linha;

        if (dataSet == null) {

            linha = new ViewHolder();

            LayoutInflater layoutResultadoFinalList = LayoutInflater.from(getContext());

            dataSet = layoutResultadoFinalList.inflate(R.layout.linha_screen_home, parent, false);

            linha.tvPedidoHome = dataSet.findViewById(R.id.tvPedidoHome);
            linha.tvPrioridade = dataSet.findViewById(R.id.tvPrioridade);
            linha.btnClienteHome = dataSet.findViewById(R.id.btnClienteHome);
            linha.btnFinalizarPedido = dataSet.findViewById(R.id.btnFinalizarPedido);
            linha.btnVisualizarPedidoHome = dataSet.findViewById(R.id.btnVisualizarPedidoHome);

            dataSet.setTag(linha);

        } else {
            linha = (ViewHolder) dataSet.getTag();
        }

        clienteRepository = new ClienteRepository(context);
        Cliente cliente = clienteRepository.getById(pedido.getClienteId());

        linha.tvPrioridade.setText(pedido.getPrioridade().getLabel());
        if(pedido.getPrioridade() == Prioridade.NORMAL) linha.tvPrioridade.setTextColor(Color.parseColor("#0000ff"));

        linha.tvPedidoHome.setText(pedido.getDescricao());
        String nomeCliente  = !cliente.getApelido().isEmpty() ? cliente.getNome() + " (" + cliente.getApelido() + ")" : cliente.getNome();
        linha.btnClienteHome.setText(nomeCliente);

        linha.btnFinalizarPedido.setOnClickListener(this);
        linha.btnFinalizarPedido.setTag(position);

        linha.btnVisualizarPedidoHome.setOnClickListener(this);
        linha.btnVisualizarPedidoHome.setTag(position);

        return dataSet;
    }

}