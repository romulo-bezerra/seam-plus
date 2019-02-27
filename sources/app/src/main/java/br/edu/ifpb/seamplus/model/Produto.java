package br.edu.ifpb.seamplus.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Produto {

    private long id;
    private String nome;
    private String descricao;
    private int quantidade;
    private double valorDeCompra;
    private double valorDeVenda;
    private double comprimento;
    private double largura;
    private Date dataInsercao;

}