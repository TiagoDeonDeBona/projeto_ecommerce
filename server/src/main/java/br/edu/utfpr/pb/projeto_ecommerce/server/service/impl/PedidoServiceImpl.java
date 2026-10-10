package br.edu.utfpr.pb.projeto_ecommerce.server.service.impl;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.ItemPedidoDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.PedidoDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.mapper.ItemPedidoMapper;
import br.edu.utfpr.pb.projeto_ecommerce.server.mapper.PedidoMapper;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.*;
import br.edu.utfpr.pb.projeto_ecommerce.server.repository.PedidoRepository;
import br.edu.utfpr.pb.projeto_ecommerce.server.repository.ProductRepository;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.AuthService;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.IAddressService;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.IPedidoService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final AuthService authService;
    private final IAddressService addressService; // responsavel de buscar o endereço e verifica se é do usuario
    private final ProductRepository productRepository; // busca o produto e o preco direto do banco
    private final PedidoMapper pedidoMapper; // transforma de DTO para pedido
    private final ItemPedidoMapper itemPedidoMapper; // transforma de DTO para pedido

    public PedidoServiceImpl(PedidoRepository pedidoRepository, AuthService authService, IAddressService addressService, ProductRepository productRepository, PedidoMapper pedidoMapper, ItemPedidoMapper itemPedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.authService = authService;
        this.addressService = addressService;
        this.productRepository = productRepository;
        this.pedidoMapper = pedidoMapper;
        this.itemPedidoMapper = itemPedidoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = (User) authService.loadUserByUsername(username);

        return pedidoRepository.findByUserId(user.getId());
    }

    @Override
    @Transactional // vamos usar para que caso haja algum problema nessa transação que foi aberta as alteracoes sejam revertidas
    public Pedido save(PedidoDTO pedidoDTO) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = (User) authService.loadUserByUsername(username);

        // recupera o endereço pelo id do DTO
        Address address = addressService.findById(pedidoDTO.getAddressId());

        if (address == null) {
            return null;
        } // sem endereço nao tem como ter pedido

        // cria o pedido atraves das informacoes do DTO
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);

        pedido.setUser(user);
        pedido.setAddress(address);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setItemPedido(new ArrayList<>());

        // tem que definir e atribuir o valor zerado pois ele começa null
        // nao tem como somarmos o null + valor do item
        BigDecimal valorTotal = BigDecimal.ZERO;

        for(ItemPedidoDTO itemDTO : pedidoDTO.getItemPedido()){
            // para cada item de pedido que eu tenho
            // vou usar o repository para pegar o productId do DTO itemPedidoDTO
            // e pegar o objeto completo
            Product product = productRepository
                    .findById(itemDTO.getProductId())
                    .orElse(null);
            if(product == null){
                return null;
            }

            // converto meu itemPedidoDTO para entidade
            ItemPedido item =  itemPedidoMapper.toEntity(itemDTO);

            // vamos completar as informacoes que o mapper ignorou
            item.setProduct(product);
            item.setPedido(pedido); // vai ser o que virou entidade antes do for
            item.setPrecoUnitario(product.getPrice());

            // adiciono o item agora completo para meu pedido
            pedido.getItemPedido().add(item);

            // vou fazer o subtotal por item ou seja multiplicar o preço do produto pela quantidade de itens
            BigDecimal subTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantidade()));

            valorTotal = valorTotal.add(subTotal);
        }
        pedido.setValorTotal(valorTotal);

        return pedidoRepository.save(pedido);
    }

}
