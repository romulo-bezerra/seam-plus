package br.edu.ifpb.seamplus.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import br.edu.ifpb.seamplus.database.converters.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Divida.class,
                parentColumns = "id",
                childColumns = "dividaId"
        )
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Desconto implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @TypeConverters(DateConverter.class)
    private Date data;
    private double valor;
    @ColumnInfo(index = true)
    private long dividaId;

    @Ignore
    public Desconto(Date data, double valor, long dividaId) {
        this.data = data;
        this.valor = valor;
        this.dividaId = dividaId;
    }
}