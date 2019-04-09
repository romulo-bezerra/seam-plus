package br.edu.ifpb.seamplus.database.converters;

import android.arch.persistence.room.TypeConverter;

import br.edu.ifpb.seamplus.model.enums.Sexo;

public class SexoConverter {

    @TypeConverter
    public static Sexo fromSexo(String value) {
        return value == null ? null : Sexo.valueOf(value.toUpperCase());
    }

    @TypeConverter
    public static String sexoToString(Sexo sexo) {
        return sexo == null ? null : sexo.getLabel();
    }

}
