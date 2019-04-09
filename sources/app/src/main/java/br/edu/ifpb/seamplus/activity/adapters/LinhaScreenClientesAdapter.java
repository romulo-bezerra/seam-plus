package br.edu.ifpb.seamplus.activity.adapters;

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
import java.util.List;

import br.edu.ifpb.seamplus.R;
import br.edu.ifpb.seamplus.activity.DividaActivity;
import br.edu.ifpb.seamplus.activity.EdicaoClienteActivity;
import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Desconto;
import br.edu.ifpb.seamplus.model.Divida;
import br.edu.ifpb.seamplus.model.Pedido;
import br.edu.ifpb.seamplus.repository.ClienteRepository;
import br.edu.ifpb.seamplus.repository.DescontoRepository;
import br.edu.ifpb.seamplus.repository.DividaRepository;
import br.edu.ifpb.seamplus.repository.PedidoRepository;

public class LinhaScreenClientesAdapter extends ArrayAdapter<Cliente> implements View.OnClickListener {

    Context context;

    private int ultimaPosicao = -1;

    AlertDialog.Builder builder;
    AlertDialog alert;

    ArrayList<Cliente> clientes;
    Cliente cliente;

    private PedidoRepository pedidoRepository;
    private DescontoRepository descontoRepository;
    private ClienteRepository clienteRepository;
    private DividaRepository dividaRepository;

    private static class ViewHolder {

        TextView tvCliente;
        Button btnValorDividaCliente;

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

//    public void updateDataSet(ArrayList<Cliente> dataSetUpdated) {
//        this.clientes.clear();;
//        this.clientes.addAll(dataSetUpdated);
//        notifyDataSetChanged();
//    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer){
        super.registerDataSetObserver(observer);
    }

    @Override
    public void onClick(View view) {

        clienteRepository = new ClienteRepository(context);

        int posicao = (Integer) view.getTag();

        Object object = getItem(posicao);

        cliente = (Cliente) object;

        Divida divida = dividaRepository.getByClienteId(cliente.getId());

        Intent intent = new Intent(context, DividaActivity.class);
        Bundle bundle = new Bundle();

        switch (view.getId()) {

            case R.id.btnDeletarCliente:

                builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete");
                builder.setMessage("Deseja deletar este cliente?");
                builder.setCancelable(true);
                builder.setIcon(R.drawable.ic_delete_red_24dp);

                builder.setPositiveButton("SIM", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clienteRepository.delete(cliente);
                        clientes.remove(cliente);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Cliente " + cliente.getNome() + " deletado!", Toast.LENGTH_LONG).show();
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

//                Intent intent1 = new Intent();
//                Bundle dataBundle = new Bundle();
//                dataBundle.putSerializable("clienteToView", cliente);
//                intent.putExtras(dataBundle);
//                intent.setClass(context, ClientesActivity.class);

                Snackbar.make(view, "Nome: " + cliente.getNome() + "  ( " + cliente.getApelido() +" ) "
                        + "\n" + "Sexo: " + cliente.getSexo().getLabel() + "\n", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;

            case R.id.btnEditarCliente:
                Intent intentEditCliente = new Intent(context, EdicaoClienteActivity.class);
                Bundle daBundleEditCliente = new Bundle();
                daBundleEditCliente.putSerializable("clienteParaEdicao", cliente);
                intentEditCliente.putExtras(daBundleEditCliente);
                context.startActivity(intentEditCliente);
                break;

            case R.id.btnValorDividaCliente:
                bundle.putSerializable("divida", divida);
                bundle.putSerializable("clienteParaDivida", cliente);
                intent.putExtras(bundle);
                context.startActivity(intent);

                break;

            case R.id.btnDescontarValorDivida:
                bundle.putSerializable("divida", divida);
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

        dividaRepository = new DividaRepository(context);
        pedidoRepository = new PedidoRepository(context);
        descontoRepository = new DescontoRepository(context);

        ViewHolder linha;

        if (dataSet == null) {

            linha = new ViewHolder();

            LayoutInflater layoutResultadoFinalList = LayoutInflater.from(getContext());

            dataSet = layoutResultadoFinalList.inflate(R.layout.linha_screen_clientes, parent, false);

            linha.tvCliente = dataSet.findViewById(R.id.tvCliente);
            linha.btnEditarCliente = dataSet.findViewById(R.id.btnEditarCliente);
            linha.btnDescontarValorDivida = dataSet.findViewById(R.id.btnDescontarValorDivida);
            linha.btnValorDividaCliente = dataSet.findViewById(R.id.btnValorDividaCliente);
            linha.btnDeletarCliente = dataSet.findViewById(R.id.btnDeletarCliente);
            linha.btnVisualizarCliente = dataSet.findViewById(R.id.btnVisualizarCliente);

            dataSet.setTag(linha);

        } else {
            linha = (ViewHolder) dataSet.getTag();
        }

        String campoNome = !cliente.getApelido().isEmpty() ? cliente.getNome() + " - " + cliente.getApelido() : cliente.getNome();
        linha.tvCliente.setText(campoNome);

        double totalDividaCliente = 0;
        for (Pedido pedido : pedidoRepository.getAllByClienteId(cliente.getId())){
            totalDividaCliente += pedido.getValor();
        }

        boolean quitada = totalDividaCliente == 0;
        Divida divida;

        if (dividaRepository.checaDivida(cliente.getId()) == 0){
            divida = new Divida(totalDividaCliente, quitada, cliente.getId());
            dividaRepository.save(divida);
        } else {
            divida = dividaRepository.getByClienteId(cliente.getId());

            divida.setTotal(totalDividaCliente);
            divida.setQuitada(quitada);

            dividaRepository.update(divida);
        }

        List<Desconto> descontosDaDivida = descontoRepository.getAllByDividaId(divida.getId());
        double valorDividaComDesconto = divida.calcularDividaComDescontos(descontosDaDivida);

        linha.btnValorDividaCliente.setText("R$ " + String.valueOf(valorDividaComDesconto));
        linha.btnValorDividaCliente.setOnClickListener(this);
        linha.btnValorDividaCliente.setTag(position);

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