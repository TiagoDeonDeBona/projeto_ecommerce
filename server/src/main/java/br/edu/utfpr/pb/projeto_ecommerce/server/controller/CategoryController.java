package br.edu.utfpr.pb.projeto_ecommerce.server.controller;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.CategoryDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.mapper.CategoryMapper;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.Category;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.ICategoryService;
import br.edu.utfpr.pb.projeto_ecommerce.server.shared.GenericResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private final CategoryMapper categoryMapper;
    private final ICategoryService categoryService;

    public CategoryController(CategoryMapper categoryMapper, ICategoryService categoryService){
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> save(@RequestBody @Valid CategoryDTO categoryDTO){
        this.categoryService.save(categoryMapper.toEntity(categoryDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse("Categoria salva com sucesso."));
    }

    @PutMapping
    public ResponseEntity<GenericResponse> update(@RequestBody @Valid CategoryDTO categoryDTO){
        this.categoryService.save(categoryMapper.toEntity(categoryDTO));

        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse("Categoria alterada com sucesso."));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
        return ResponseEntity.ok(
                categoryService.findAll()
                        .stream() // pega os elementos da minha lista e retorna 1 por 1 em sequencia
                        .map(categoryMapper::toDto) // map = para todos elemento faça algo nesse casso transformar em DTO
                        .collect(Collectors.toList()) // volta novamente para lista e retorna com status OK
        );
    }

    @GetMapping("{id}") // sempre passar entre parenteses o parametros que vamos receber da requisicao
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        Category category = categoryService.findById(id);

        if (category != null){
            return ResponseEntity.status(HttpStatus.OK).body(categoryMapper.toDto(category));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status 204 de quando a exclusao deu certa
    // como ele fez aqui para dar a resposta da API nao precisa fazer ResponseEntity, porem dava para fazer da mesma forma
    // com response entity ou com GenericResponse
    public void delete(@PathVariable Long id){
        categoryService.deleteById(id);
    }

    //http://localhost:8080/categories/page?page=1&size=5
    @GetMapping("page")
    public ResponseEntity<Page<CategoryDTO>> findPage(@RequestParam int page,
                                                      @RequestParam int size,
                                                      @RequestParam(required = false) String order,
                                                      @RequestParam(required = false) Boolean asc
                                                      ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null){
            pageRequest = PageRequest.of(page, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(pageRequest).map(categoryMapper::toDto));
    }


}
