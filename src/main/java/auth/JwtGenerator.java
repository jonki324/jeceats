package auth;

import com.ibm.websphere.security.jwt.JwtBuilder;

import entity.Role;

public class JwtGenerator {
    public String getToken(Integer id, String loginId, Role role) throws Exception {
        JwtBuilder jb = JwtBuilder.create("defaultJWT");
        jb.subject(loginId);
        jb.claim("upn", loginId);
        jb.claim("id", id);
        jb.claim("groups", role);
        return jb.buildJwt().compact();
    }
}
