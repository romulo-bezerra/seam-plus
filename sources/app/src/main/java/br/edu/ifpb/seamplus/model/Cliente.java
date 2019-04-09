package br.edu.ifpb.seamplus.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;

import br.edu.ifpb.seamplus.database.converters.SexoConverter;
import br.edu.ifpb.seamplus.model.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(foreignKeys =
    @ForeignKey(
        entity = Atelie.class,
        parentColumns = "id",
        childColumns = "atelieId"
    )
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nome;
    private String apelido;
    @TypeConverters(SexoConverter.class)
    private Sexo sexo;
    @ColumnInfo(index = true)
    private long atelieId;

    @Ignore
    public Cliente(String nome, String apelido, Sexo sexo, long atelieId){
        this.nome = nome;
        this.apelido = apelido;
        this.sexo = sexo;
        this.atelieId = atelieId;
    }

    //    private Divida divida;
//    List<Pedido> pedidos;


//    public Cliente() {
//        pedidos = new ArrayList<>();
//    }

}