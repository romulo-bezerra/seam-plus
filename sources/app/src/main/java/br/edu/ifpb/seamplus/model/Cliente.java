package br.edu.ifpb.seamplus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.seamplus.model.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
public class Cliente implements Serializable {

    private long id;
    @NonNull private String nome;
    private String apelido;
    @NonNull private Sexo sexo;
    private Divida divida;
    List<Pedido> pedidos;

    public Cliente() {
        pedidos = new ArrayList<>();
    }

}