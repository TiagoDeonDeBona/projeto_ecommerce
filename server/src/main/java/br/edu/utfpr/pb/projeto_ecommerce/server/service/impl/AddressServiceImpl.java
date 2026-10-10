package br.edu.utfpr.pb.projeto_ecommerce.server.service.impl;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Address;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import br.edu.utfpr.pb.projeto_ecommerce.server.repository.AddressRepository;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.AuthService;
import br.edu.utfpr.pb.projeto_ecommerce.server.service.IAddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements IAddressService {
    private final AddressRepository addressRepository;
    private final AuthService authService;

    public AddressServiceImpl (AddressRepository addressRepository, AuthService authService){
        this.addressRepository = addressRepository;
        this.authService = authService;
    }

    @Override
    public Address save(Address address) {

        String username = SecurityContextHolder // metodo que o spring usa para devolver a autenticacao atual
                .getContext() // acessa o contexto de segurança
                .getAuthentication() //recupera a autenticacao do usuario
                .getName(); // pega o usernaame

        User user = (User) authService.loadUserByUsername(username);
        // aqui o (User) entre parenteses é por conta que o authService retorna um UserDetails estamos fazendo a conversao para um User normal.

        // Garanto que o post para Address seja tratado sempre como um novo cadastro
        address.setId(null);

        // associo o usuario autenticado para o endereço
        address.setUser(user);

        return this.addressRepository.save(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findAll() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = (User) authService.loadUserByUsername(username);

        List<Address> lista = this.addressRepository.findAll();

        return lista.stream()
                .filter(address -> address.getUser().equals(user))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public Address findById(Long id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = (User) authService.loadUserByUsername(username);
        Address address = this.addressRepository.findById(id).orElse(null);

        if (address == null){
            return null; // verifica antes de entrar no comparador
        }

        if (user.equals(address.getUser())) {
            return address;
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = (User) authService.loadUserByUsername(username);
        Address address = this.addressRepository.findById(id).orElse(null);

        if (address != null && user.equals(address.getUser())) {
            this.addressRepository.deleteById(id);
        }
    }

    @Override
    public Address update(Long id, Address addressUpdate) {
        Address address = findById(id);

        if (address == null){
            return null;
        }

        // Atualiza somente os dados do endereço
        address.setCep(addressUpdate.getCep());
        address.setRua(addressUpdate.getRua());
        address.setNumero(addressUpdate.getNumero());
        address.setComplemento(addressUpdate.getComplemento());
        address.setEstado(addressUpdate.getEstado());
        address.setCidade(addressUpdate.getCidade());
        address.setRegiao(addressUpdate.getRegiao());
        address.setUf(addressUpdate.getUf());
        address.setDdd(addressUpdate.getDdd());
        address.setNome_recebimento(addressUpdate.getNome_recebimento());
        address.setTelefone_contato(addressUpdate.getTelefone_contato());

        return addressRepository.save(address);
    }
}
