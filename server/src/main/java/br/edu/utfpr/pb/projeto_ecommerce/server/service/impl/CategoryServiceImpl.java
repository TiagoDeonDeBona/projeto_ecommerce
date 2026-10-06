package br.edu.utfpr.pb.projeto_ecommerce.server.service.impl;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Category;
import br.edu.utfpr.pb.projeto_ecommerce.server.repository.CategoryRepository;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true) // toda conversa com o banco abre uma transacao
    // aqui esta dizendo que a transicao com o banco é somente de leitura nao vai alterar nada
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }
    // falta delete + pageFindAll
}
