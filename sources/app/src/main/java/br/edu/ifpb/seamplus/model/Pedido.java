package br.edu.ifpb.seamplus.model;

import java.io.Serializable;
import java.util.Date;

import br.edu.ifpb.seamplus.model.enums.Prioridade;
import br.edu.ifpb.seamplus.model.enums.SituacaoPedido;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pedido implements Serializable {

    private long id;
    private String descricao;
    private double valor;
    private Date dataRealizacao;
    private boolean finalizado;
    private Prioridade prioridade;
    private SituacaoPedido situacaoPedido;
    private Cliente cliente;

}