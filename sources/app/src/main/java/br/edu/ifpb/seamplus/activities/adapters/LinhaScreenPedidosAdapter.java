package br.edu.ifpb.seamplus.activities.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.activities.EdicaoPedidoActivity;
import br.edu.ifpb.seamplus.model.Pedido;

public class LinhaScreenPedidosAdapter extends ArrayAdapter<Pedido> implements View.OnClickListener {

    Context context;

    private int ultimaPosicao = -1;

    AlertDialog.Builder builder;
    AlertDialog alert;

    ArrayList<Pedido> pedidos;
    Pedido pedido;

    private static class ViewHolder {

        TextView tvPedido;
        TextView tvCliente;

        ImageButton btnVisualizarPedido;
        ImageButton btnDeletarPedido;
        ImageButton btnEditarPedido;
    }

    public LinhaScreenPedidosAdapter(Context context, ArrayList<Pedido> dataSet) {
        super(context, R.layout.linha_screen_pedidos, dataSet);

        this.pedidos = dataSet;

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

            case R.id.btnDeletarPedidoTelaPedidos:

                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Alerta");
                builder.setMessage("Deseja deletar este pedido?");
                builder.setCancelable(true);
                builder.setIcon(R.mipmap.ic_launcher);

                builder.setPositiveButton("SIM", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(pedidos.remove(pedido)) {
                            notifyDataSetChanged();
                            Toast.makeText(context, "Pedido " + pedido.getDescricao() + " deletado!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Não foi possível deletar o pedido!", Toast.LENGTH_LONG).show();
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


            case R.id.btnVisualizarPedidoTelaPedidos:

                Snackbar.make(view, "Pedido: " + pedido.getDescricao()
                        + "\n" + "Cliente: " + pedido.getCliente().getNome() + "\n", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;

            case R.id.btnEditarPedido:

                context.startActivity(new Intent(context, EdicaoPedidoActivity.class));

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

            dataSet = layoutResultadoFinalList.inflate(R.layout.linha_screen_pedidos, parent, false);

            linha.tvPedido = dataSet.findViewById(R.id.tvPedidoTelaPedidos);
            linha.tvCliente = dataSet.findViewById(R.id.tvClienteTelaPedidos);

            linha.btnEditarPedido = dataSet.findViewById(R.id.btnEditarPedido);
            linha.btnDeletarPedido = dataSet.findViewById(R.id.btnDeletarPedidoTelaPedidos);
            linha.btnVisualizarPedido = dataSet.findViewById(R.id.btnVisualizarPedidoTelaPedidos);

            dataSet.setTag(linha);

        } else {
            linha = (ViewHolder) dataSet.getTag();
        }

        linha.tvPedido.setText(pedido.getDescricao());
        linha.tvCliente.setText(pedido.getCliente().getNome());

        linha.btnDeletarPedido.setOnClickListener(this);
        linha.btnDeletarPedido.setTag(position);

        linha.btnEditarPedido.setOnClickListener(this);
        linha.btnEditarPedido.setTag(position);

        linha.btnVisualizarPedido.setOnClickListener(this);
        linha.btnVisualizarPedido.setTag(position);

        return dataSet;
    }

}