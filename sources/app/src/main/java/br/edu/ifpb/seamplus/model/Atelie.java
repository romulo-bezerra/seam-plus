package br.edu.ifpb.seamplus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class Atelie implements Serializable {

    private long id;
    @NonNull private String nomeFantasia;
    private Estoque estoque;
    private List<Cliente> clientes;

    public Atelie() {
        clientes = new ArrayList<>();
    }

}