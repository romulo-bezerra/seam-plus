package br.edu.ifpb.seamplus.activities.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.activities.DividaActivity;
import br.edu.ifpb.seamplus.activities.EdicaoClienteActivity;
import br.edu.ifpb.seamplus.activities.MainActivity;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Divida;

public class LinhaScreenClientesAdapter extends ArrayAdapter<Cliente> implements View.OnClickListener {

    Context context;

    private int ultimaPosicao = -1;

    AlertDialog.Builder builder;
    AlertDialog alert;

    ArrayList<Cliente> clientes;
    Cliente cliente;

    private static class ViewHolder {

        TextView tvCliente;
        Button btnDividaCliente;

        ImageButton btnDescontarValorDivida;
        ImageButton btnVisualizarCliente;
        ImageButton btnDeletarCliente;
        ImageButton btnEditarCliente;
    }

    public LinhaScreenClientesAdapter(Context context, ArrayList<Cliente> dataSet) {
        super(context, R.layout.linha_screen_clientes, dataSet);

        this.clientes = dataSet;

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

        cliente = (Cliente) object;

        Intent intent = new Intent(context, DividaActivity.class);
        Bundle bundle = new Bundle();

        switch (view.getId()) {

            case R.id.btnDeletarCliente:

                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Alerta");
                builder.setMessage("Deseja deletar este cliente?");
                builder.setCancelable(true);
                builder.setIcon(R.mipmap.ic_launcher);

                builder.setPositiveButton("SIM", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(clientes.remove(cliente)) {
                            notifyDataSetChanged();
                            Toast.makeText(context, "Cliente " + cliente.getNome() + " deletado!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Não foi possível deletar o cliente!", Toast.LENGTH_LONG).show();
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


            case R.id.btnVisualizarCliente:

                Snackbar.make(view, "Cliente: " + cliente.getNome()
                        + "\n" + "Dívida: " + cliente.getDivida().getTotal() + "\n", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;

            case R.id.btnEditarCliente:
                context.startActivity(new Intent(context, EdicaoClienteActivity.class));
                break;

            case R.id.btnDividaCliente:
                bundle.putSerializable("clienteParaDivida", cliente);
                intent.putExtras(bundle);
                context.startActivity(intent);

                break;

            case R.id.btnDescontarValorDivida:
                bundle.putSerializable("clienteParaDivida", cliente);
                intent.putExtras(bundle);
                context.startActivity(intent);

                break;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View dataSet, @NonNull ViewGroup parent) {

        cliente = getItem(position);

        ViewHolder linha;

        if (dataSet == null) {

            linha = new ViewHolder();

            LayoutInflater layoutResultadoFinalList = LayoutInflater.from(getContext());

            dataSet = layoutResultadoFinalList.inflate(R.layout.linha_screen_clientes, parent, false);

            linha.tvCliente = dataSet.findViewById(R.id.tvCliente);
            linha.btnEditarCliente = dataSet.findViewById(R.id.btnEditarCliente);
            linha.btnDescontarValorDivida = dataSet.findViewById(R.id.btnDescontarValorDivida);
            linha.btnDividaCliente = dataSet.findViewById(R.id.btnDividaCliente);
            linha.btnDeletarCliente = dataSet.findViewById(R.id.btnDeletarCliente);
            linha.btnVisualizarCliente = dataSet.findViewById(R.id.btnVisualizarCliente);

            dataSet.setTag(linha);

        } else {
            linha = (ViewHolder) dataSet.getTag();
        }

        linha.tvCliente.setText(cliente.getNome());

        linha.btnDividaCliente.setText("R$ " + String.valueOf(cliente.getDivida().getTotal()));
        linha.btnDividaCliente.setOnClickListener(this);
        linha.btnDividaCliente.setTag(position);

        linha.btnDeletarCliente.setOnClickListener(this);
        linha.btnDeletarCliente.setTag(position);

        linha.btnEditarCliente.setOnClickListener(this);
        linha.btnEditarCliente.setTag(position);

        linha.btnVisualizarCliente.setOnClickListener(this);
        linha.btnVisualizarCliente.setTag(position);

        linha.btnDescontarValorDivida.setOnClickListener(this);
        linha.btnDescontarValorDivida.setTag(position);

        return dataSet;
    }

}