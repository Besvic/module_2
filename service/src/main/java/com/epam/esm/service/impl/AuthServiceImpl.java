package com.epam.esm.service.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.pojo.security.ERole;
import com.epam.esm.pojo.security.JwtResponse;
import com.epam.esm.pojo.security.LoginRequest;
import com.epam.esm.pojo.security.SignUpRequest;
import com.epam.esm.security.UserDetailsImpl;
import com.epam.esm.service.AuthService;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.util.LocalizedMessage.getMessageForLocale;

/**
 * The type Auth service.
 */
@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Instantiates a new Auth service.
     *
     * @param userRepository        the user repository
     * @param roleService           the role service
     * @param authenticationManager the authentication manager
     * @param jwtUtil               the jwt util
     */
    public AuthServiceImpl(UserService userRepository,
                           RoleService roleService,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil) {
        this.userService = userRepository;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User registration(SignUpRequest signUpRequest) throws ServiceException {
        if (userService.existByEmail(signUpRequest.getEmail())){
            log.warn(getMessageForLocale("change.email"));
            throw new ServiceException("change.email");
        }

        User user = User.builder()
                .name(signUpRequest.getName())
                .surname(signUpRequest.getSurname())
                .lastname(signUpRequest.getLastname())
                .balance(signUpRequest.getBalance())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .build();

        List<String> reqRoles = signUpRequest.getRoleList();
        List<Role> roles = new ArrayList<>();

        if (reqRoles == null) {
            Role userRole = roleService
                    .findByName(ERole.ROLE_GUEST.name());
            roles.add(userRole);
        } else {
            for (var r: signUpRequest.getRoleList()) {
                switch (r) {
                    case "admin":
                        Role adminRole = roleService
                                .findByName(ERole.ROLE_ADMIN.name());
                        roles.add(adminRole);
                        break;
                    case "user":
                        Role modRole = roleService
                                .findByName(ERole.ROLE_USER.name());
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleService
                                .findByName(ERole.ROLE_GUEST.name());
                        roles.add(userRole);
                }
            }
        }
        user.setRoleList(roles);
        return userService.create(user);
    }

    @Override
    public JwtResponse authentication(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new JwtResponse(jwt, userDetails.getId(), roles);
    }
}
