package br.edu.utfpr.pb.projeto_ecommerce.server.service;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Address;

import java.util.List;

public interface IAddressService {
    List<Address> findAll();
    Address save(Address address);
    Address findById(Long id);
    void deleteById(Long id);
    Address update(Long id, Address address);
}
