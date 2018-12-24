package br.edu.ifpb.seamplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class LinhaScreenPedidosAdapter extends ArrayAdapter<TempObject> {

    private final Context context;
    private final ArrayList<TempObject> elementos;

    public LinhaScreenPedidosAdapter(Context context, ArrayList<TempObject> elementos) {
        super(context, R.layout.linha_screen_home, elementos);
        this.context= context;
        this.elementos= elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_screen_pedidos, parent, false);
        TextView string1 = rowView.findViewById(R.id.tvPedido);
        Button string2 =  rowView.findViewById(R.id.btnCliente);

        string1.setText(elementos.get(position).getString1());
        string2.setText(elementos.get(position).getString2());

        return rowView;
    }
}