package dto.mapper;

import dto.UserDTO;
import entity.User;
import resource.request.LoginRequest;

public class UserDTOMapper {
    public UserDTO mapToDTO(LoginRequest req) {
        return new UserDTO().setLoginId(req.getLoginId()).setPassword(req.getPassword());
    }

    public UserDTO mapToDTO(User entity) {
        return new UserDTO().setId(entity.getId()).setLoginId(entity.getLoginId()).setPassword(entity.getPassword())
                .setName(entity.getName()).setRole(entity.getRole());
    }
}
