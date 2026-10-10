package br.edu.utfpr.pb.projeto_ecommerce.server.controller;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.AddressDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.ViaCepDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.mapper.AddressMapper;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.Address;
import br.edu.utfpr.pb.projeto_ecommerce.server.repository.AddressRepository;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.IAddressService;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.ViaCepService;
import br.edu.utfpr.pb.projeto_ecommerce.server.shared.GenericResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("addresses")
public class AddressController {
    private final AddressMapper addressMapper;
    private final IAddressService addressService;
    private final ViaCepService viaCepService;

    public AddressController(AddressMapper addressMapper, IAddressService addressService, ViaCepService viaCepService){
        this.addressMapper = addressMapper;
        this.addressService = addressService;
        this.viaCepService = viaCepService;
    }

    @PostMapping
    public ResponseEntity<GenericResponse> save(@RequestBody @Valid AddressDTO addressDTO) {
        this.addressService.save(addressMapper.toEntity(addressDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse("Endereço cadastrado com sucesso!"));
    }

    @PutMapping("{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable Long id, @RequestBody @Valid AddressDTO addressDTO){
        Address address = addressService.update(id, addressMapper.toEntity(addressDTO));

        if (address != null){
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse("Endereço alterado com sucesso!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(
                addressService.findAll()
                        .stream()
                        .map(addressMapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id){
        Address address = addressService.findById(id);

        if(address != null) {
            return ResponseEntity.status(HttpStatus.OK).body(addressMapper.toDTO(address));
        } else {
            return ResponseEntity.notFound().build(); // recomendado notFound para quando é um
            // endereço inexistente ou nao autorizado para quele usuarioQ
        }
    }

    @DeleteMapping("{id}") // @PathVariable pega variavel que enviamos no caminho ou seja oque vem depois do /
    // addresses/5 -> numero 5 é o PathVariable
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Address address = addressService.findById(id);

        if (address != null) {
            this.addressService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<ViaCepDTO> consultarCep(@PathVariable String cep){
        try { // recebe a requisição
            ViaCepDTO endereco = viaCepService.consultarCep(cep); // tenta consultar o cep e fazer o DTO

            if (endereco != null){ // se nao vier nulo
                return ResponseEntity.status(HttpStatus.OK).body(endereco); // salva normal
            } else {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();// ate tem o cep valido porem nao foi encontrado
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // enviou o cep invalido
        }
    }
}
