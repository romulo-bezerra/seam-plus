package br.edu.ifpb.seamplus.database.converters;

import android.arch.persistence.room.TypeConverter;

import br.edu.ifpb.seamplus.model.enums.Prioridade;

public class PrioridadeConverter {

    @TypeConverter
    public static Prioridade fromPrioridade(String value) {
        return value == null ? null : Prioridade.valueOf(value.toUpperCase());
    }

    @TypeConverter
    public static String prioridadeToString(Prioridade prioridade) {
        return prioridade == null ? null : prioridade.getLabel();
    }

}
