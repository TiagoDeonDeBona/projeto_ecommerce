package br.edu.utfpr.pb.projeto_ecommerce.server.repository;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUserId(Long userId);
    // como o Repository nao tem esse metodo por padrao implementado
    // temos que fazer para Listar os pedidos pelo id do usuario
}
