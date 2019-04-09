package br.edu.ifpb.seamplus.database.converters;

import android.arch.persistence.room.TypeConverter;

import br.edu.ifpb.seamplus.model.enums.SituacaoPedido;

public class SituacaoPedidoConverter {

    @TypeConverter
    public static SituacaoPedido fromSituacaoPedido(String value) {
        return value == null ? null : SituacaoPedido.valueOf(value.toUpperCase());
    }

    @TypeConverter
    public static String situacaoPedidoToString(SituacaoPedido situacaoPedido) {
        return situacaoPedido == null ? null : situacaoPedido.getLabel();
    }

}
