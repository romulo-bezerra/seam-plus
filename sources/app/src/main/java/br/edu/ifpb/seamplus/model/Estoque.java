package br.edu.ifpb.seamplus.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Estoque {

    private long id;
    private List<Produto> produtos;

    public Estoque() {
        produtos = new ArrayList<>();
    }

}