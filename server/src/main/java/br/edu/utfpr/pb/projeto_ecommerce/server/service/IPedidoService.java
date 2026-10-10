package br.edu.utfpr.pb.projeto_ecommerce.server.service;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.PedidoDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.Pedido;

import java.util.List;

public interface IPedidoService {

    List<Pedido> findAll();

    Pedido save(PedidoDTO pedidoDTO);
}
