package br.edu.utfpr.pb.projeto_ecommerce.server.repository;

import br.edu.utfpr.pb.projeto_ecommerce.server.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
