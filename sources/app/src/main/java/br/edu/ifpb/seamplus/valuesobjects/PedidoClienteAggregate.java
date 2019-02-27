package br.edu.ifpb.seamplus.valuesobjects;

import br.edu.ifpb.seamplus.model.Cliente;
import br.edu.ifpb.seamplus.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoClienteAggregate {

   private Pedido pedido;
   private Cliente cliente;

}