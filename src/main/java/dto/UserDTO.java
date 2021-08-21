package dto;

import java.io.Serializable;

import entity.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String loginId;

    private String password;

    private String name;

    private Role role;

    private String token;
}
