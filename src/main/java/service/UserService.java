package service;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import auth.JwtGenerator;
import common.Constants.ErrorType;
import dao.UserDAO;
import dto.LoginInputDTO;
import dto.LoginOutputDTO;
import dto.UserDTO;
import entity.User;

@RequestScoped
public class UserService extends BaseService {
    private JwtGenerator jwtGenerator = new JwtGenerator();

    @Inject
    private UserDAO userDAO;

    public LoginOutputDTO login(LoginInputDTO inputDTO) {
        LoginOutputDTO outputDTO = new LoginOutputDTO();
        Optional<User> user = userDAO.findByLoginIdAndPassword(inputDTO.getLoginId(), inputDTO.getPassword());
        user.ifPresentOrElse(u -> {
            String jwt = null;
            try {
                jwt = jwtGenerator.getToken(u.getId(), u.getLoginId(), u.getRole());
            } catch (Exception e) {
                throw createAppException(ErrorType.LOGIN_ERROR, e);
            }
            outputDTO.setToken(jwt);
            UserDTO userDTO = new UserDTO();
            userDTO.setId(u.getId());
            userDTO.setLoginId(u.getLoginId());
            userDTO.setName(u.getName());
            userDTO.setRole(u.getRole());
            outputDTO.setUser(userDTO);
        }, () -> {
            throw createAppException(ErrorType.LOGIN_ERROR);
        });
        return outputDTO;
    }
}
