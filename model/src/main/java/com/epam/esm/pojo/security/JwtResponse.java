package com.epam.esm.pojo.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Jwt response.
 */
@Data
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private List<String> roleList;

    /**
     * Instantiates a new Jwt response.
     *
     * @param token    the token
     * @param id       the id
     * @param roleList the role list
     */
    public JwtResponse(String token, Long id, List<String> roleList) {
        this.token = token;
        this.id = id;
        this.roleList = roleList;
    }
}
