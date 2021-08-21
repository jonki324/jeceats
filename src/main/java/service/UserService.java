package service;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import auth.JwtGenerator;
import dao.UserDAO;
import dto.UserDTO;
import dto.mapper.UserDTOMapper;
import entity.User;

@RequestScoped
public class UserService extends BaseService {
    @Inject
    protected JwtGenerator jwtGenerator;

    @Inject
    protected UserDAO userDAO;

    public UserDTO login(UserDTO inputDTO) {
        Optional<User> optUser = userDAO.findByLoginIdAndPassword(inputDTO.getLoginId(), inputDTO.getPassword());
        var user = optUser.orElseThrow(() -> {
            throw createValidationException(msgConfig.INVALID_LOGIN);
        });
        var userDTO = new UserDTOMapper().mapToDTO(user);
        try {
            var jwt = jwtGenerator.getToken(user.getId(), user.getLoginId(), user.getRole());
            userDTO.setToken(jwt);
        } catch (Exception e) {
            throw createValidationException(msgConfig.INVALID_LOGIN, e);
        }
        return userDTO;
    }
}
