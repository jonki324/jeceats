package dto.mapper;

import dto.UserDTO;
import resource.request.LoginRequest;

public class UserDTOMapper {
    public UserDTO mapToDTO(LoginRequest req) {
        return new UserDTO().setLoginId(req.getLoginId()).setPassword(req.getPassword());
    }
}
