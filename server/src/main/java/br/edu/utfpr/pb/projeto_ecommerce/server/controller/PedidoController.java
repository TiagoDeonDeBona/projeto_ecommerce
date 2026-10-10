package br.edu.utfpr.pb.projeto_ecommerce.server.controller;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.PedidoDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.mapper.PedidoMapper;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.Pedido;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.IPedidoService;
import br.edu.utfpr.pb.projeto_ecommerce.server.shared.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pedidos")
public class PedidoController {
    private final IPedidoService pedidoService;
    private final PedidoMapper pedidoMapper;

    public PedidoController(IPedidoService pedidoService, PedidoMapper pedidoMapper) {
        this.pedidoService = pedidoService;
        this.pedidoMapper = pedidoMapper;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> save(@RequestBody @Valid PedidoDTO pedidoDTO){
        Pedido pedido = pedidoService.save(pedidoDTO);

        if (pedido != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new GenericResponse("Pedido realizado com Sucesso!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> findAll() {

        return ResponseEntity.status(HttpStatus.OK).body(
                pedidoService.findAll()
                        .stream()
                        .map(pedidoMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }
}
