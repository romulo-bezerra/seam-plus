package br.edu.ifpb.seamplus.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import br.edu.ifpb.seamplus.database.converters.DateConverter;
import br.edu.ifpb.seamplus.database.converters.SexoConverter;
import br.edu.ifpb.seamplus.model.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nome;
    @TypeConverters(DateConverter.class)
    private Date nascimento;
    @ColumnInfo(index = true)
    private String email;
    private String senha;
    private boolean logado;
    @TypeConverters(SexoConverter.class)
    private Sexo sexo;
    @ColumnInfo(index = true)
    private long atelieId;

    @Ignore
    public Usuario(String nome, Date nascimento, String email, String senha, Sexo sexo) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.email = email;
        this.senha = senha;
        this.sexo = sexo;
    }
}