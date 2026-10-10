package br.edu.utfpr.pb.projeto_ecommerce.server.controller;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.ProductDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.mapper.CategoryMapper;
import br.edu.utfpr.pb.projeto_ecommerce.server.mapper.ProductMapper;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.Product;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductMapper productMapper;
    private final IProductService productService;

    public ProductController(ProductMapper productMapper, IProductService productService){
        this.productMapper = productMapper;
        this.productService = productService;
    }

    // aqui o .ok(body) quer dizer a mesma coisa que o
    // .status(HttpStatus.OK).body
    // o .ok usa quando quer abreviar
    //o /status normalmente deixamos mais explicito
    // caso eu quisesse fazer um created ai sim nao  daria para fazer o .created e tem
    // que ser o .status(HttpStatus.CREATED)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(){
        return ResponseEntity.ok(
                productService.findAll()
                        .stream()
                        .map(productMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        Product product = productService.findById(id);

        if(product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.toDTO(product));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    // tem que deixar implicito que quando ele procurar por procducts/page ai sim ele cai aqui
    @GetMapping("page")
    public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam int page,
                                                    @RequestParam int size,
                                                    @RequestParam(required = false) String order,
                                                    @RequestParam(required = false) Boolean asc){
        PageRequest pageRequest = PageRequest.of(page, size);

        if (order != null && asc != null){
            pageRequest = PageRequest.of(page, size,
                asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.findAll(pageRequest).map(productMapper::toDTO));
    }
}
