package br.edu.utfpr.pb.projeto_ecommerce.server.mapper;

import br.edu.utfpr.pb.projeto_ecommerce.server.DTO.UserDTO;
import br.edu.utfpr.pb.projeto_ecommerce.server.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
// Mapper serve de identifação
// o restante indica para o Java que essa vai ser uma classe manipulada pelo spring
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    // Aqui estamos dizendo para o mapper qual atributo ignorar na hora de transforma em User
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDTO dto);

    /* basicamente ta fazendo isso
    public User toEntity(UserDTO dto) {

        User user = new User();

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }
    */

    UserDTO toDto(User entity); // mesma coisa ao contrario
}
