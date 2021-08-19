package entity.mapper;

import dto.UserDTO;
import entity.User;

public class UserEntityMapper {
    public User mapToEntity(UserDTO dto) {
        return User.builder().id(dto.getId()).loginId(dto.getLoginId()).password(dto.getPassword()).name(dto.getName())
                .role(dto.getRole()).build();
    }
}
