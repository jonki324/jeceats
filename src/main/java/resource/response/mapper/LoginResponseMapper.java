package resource.response.mapper;

import dto.UserDTO;
import resource.response.LoginResponse;

public class LoginResponseMapper {
    public LoginResponse mapToResponse(UserDTO dto) {
        return new LoginResponse().setUser(dto);
    }
}
