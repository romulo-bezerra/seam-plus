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
import br.edu.ifpb.seamplus.database.converters.PrioridadeConverter;
import br.edu.ifpb.seamplus.database.converters.SituacaoPedidoConverter;
import br.edu.ifpb.seamplus.model.enums.Prioridade;
import br.edu.ifpb.seamplus.model.enums.SituacaoPedido;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(foreignKeys = {
        @ForeignKey(
            entity = Cliente.class,
            parentColumns = "id",
            childColumns = "clienteId"
        )
//        ,
//        @ForeignKey(
//            entity = Atelie.class,
//            parentColumns = "id",
//            childColumns = "atelieId"
//        )
    }
)
@Data
@NoArgsConstructor
public class Pedido implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String descricao;
    private double valor;
    @TypeConverters(DateConverter.class)
    private Date dataRealizacao;
    private boolean finalizado;
    @TypeConverters(PrioridadeConverter.class)
    private Prioridade prioridade;
    @TypeConverters(SituacaoPedidoConverter.class)
    private SituacaoPedido situacaoPedido;
    @ColumnInfo(index = true)
    private long clienteId;
    @ColumnInfo(index = true)
    private long atelieId;

    @Ignore
    public Pedido(String descricao, double valor, Date dataRealizacao, boolean finalizado,
                  Prioridade prioridade, SituacaoPedido situacaoPedido, long clienteId, long atelieId) {
        this.descricao = descricao;
        this.valor = valor;
        this.dataRealizacao = dataRealizacao;
        this.finalizado = finalizado;
        this.prioridade = prioridade;
        this.situacaoPedido = situacaoPedido;
        this.clienteId = clienteId;
        this.atelieId = atelieId;
    }
}