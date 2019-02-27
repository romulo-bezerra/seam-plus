package br.edu.ifpb.seamplus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Divida implements Serializable {

    private long id;
    private double total;
    private boolean quitada;
    private List<Desconto> descontos;
    private Cliente cliente;

    public Divida() {
        descontos = new ArrayList<>();
    }

}