package br.edu.utfpr.pb.projeto_ecommerce.server.service;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    List<Product> findByCategory(Long idCategory);

    Page<Product> findAll(Pageable pageable);

    Product findById(Long id);
}
