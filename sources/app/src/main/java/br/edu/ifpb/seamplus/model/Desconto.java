package br.edu.ifpb.seamplus.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Desconto implements Serializable {

    private long id;
    private Date data;
    private double valor;

}