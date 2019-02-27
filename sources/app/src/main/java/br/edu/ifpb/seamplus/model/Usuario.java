package br.edu.ifpb.seamplus.model;

import java.io.Serializable;
import java.util.Date;

import br.edu.ifpb.seamplus.model.enums.Sexo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Usuario implements Serializable {

    private long id;
    private String nome;
    private Date nascimento;
    private String email;
    private String senha;
    private boolean logado;
    private Sexo sexo;
    private Atelie atelie;

    public Usuario(String nome, Date nascimento, String email, String senha, Sexo sexo) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.email = email;
        this.senha = senha;
        this.sexo = sexo;
    }
}