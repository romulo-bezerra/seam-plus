package br.edu.ifpb.seamplus.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(foreignKeys = {
        @ForeignKey(
            entity = Cliente.class,
            parentColumns = "id",
            childColumns = "clienteId"
        )
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Divida implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private double total;
    private boolean quitada;
    @ColumnInfo(index = true)
    private long clienteId;

    @Ignore
    public Divida(double total, boolean quitada, long clienteId) {
        this.total = total;
        this.quitada = quitada;
        this.clienteId = clienteId;
    }

    public double calcularDividaComDescontos(List<Desconto> descontos){

        double descontoTotal = 0;

        if (!descontos.isEmpty()){
            for (Desconto desconto : descontos){
                descontoTotal += desconto.getValor();
            }
        }
        return this.total - descontoTotal;
    }
}