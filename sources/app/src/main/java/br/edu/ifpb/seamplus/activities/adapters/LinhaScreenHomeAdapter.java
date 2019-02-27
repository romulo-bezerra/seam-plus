package br.edu.ifpb.seamplus.activities.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.activities.TempObject;

public class LinhaScreenHomeAdapter extends ArrayAdapter<TempObject> implements View.OnClickListener {

    Context context;

    private int ultimaPosicao = -1;

    AlertDialog.Builder builder;
    AlertDialog alert;

    ArrayList<TempObject> dados;
    TempObject tempObject;

    private static class ViewHolder {

        TextView tvPedidoHome;
        Button btnClienteHome;

        Button btnFinalizarPedido;
        Button btnVisualizarPedidoHome;
    }

    public LinhaScreenHomeAdapter(Context context, ArrayList<TempObject> dataSet) {
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

        tempObject = (TempObject) object;

        switch (view.getId()) {

            case R.id.btnFinalizarPedido:

                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Aerta");
                builder.setMessage("Deseja finalizar este pedido?");
                builder.setCancelable(true);
                builder.setIcon(R.mipmap.ic_launcher);

                builder.setPositiveButton("SIM", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(dados.remove(tempObject)) {
                            notifyDataSetChanged();
                            Toast.makeText(context, "Pedido " + tempObject.getString1() + "finalizado!", Toast.LENGTH_LONG).show();
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

                Snackbar.make(view, "Pedido: " + tempObject.getString1()
                        + "\n\n" + "Cliente: " + tempObject.getString2() + "\n", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View dataSet, @NonNull ViewGroup parent) {

        tempObject = getItem(position);

        ViewHolder linha;

        if (dataSet == null) {

            linha = new ViewHolder();

            LayoutInflater layoutResultadoFinalList = LayoutInflater.from(getContext());

            dataSet = layoutResultadoFinalList.inflate(R.layout.linha_screen_home, parent, false);

            linha.tvPedidoHome = dataSet.findViewById(R.id.tvPedidoHome);
            linha.btnClienteHome = dataSet.findViewById(R.id.btnClienteHome);
            linha.btnFinalizarPedido = dataSet.findViewById(R.id.btnFinalizarPedido);
            linha.btnVisualizarPedidoHome = dataSet.findViewById(R.id.btnVisualizarPedidoHome);

            dataSet.setTag(linha);

        } else {
            linha = (ViewHolder) dataSet.getTag();
        }

        linha.tvPedidoHome.setText(tempObject.getString1());
        linha.btnClienteHome.setText(tempObject.getString2());

        linha.btnFinalizarPedido.setOnClickListener(this);
        linha.btnFinalizarPedido.setTag(position);

        linha.btnVisualizarPedidoHome.setOnClickListener(this);
        linha.btnVisualizarPedidoHome.setTag(position);

        return dataSet;
    }

}