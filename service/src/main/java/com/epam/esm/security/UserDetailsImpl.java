package com.epam.esm.security;

import com.epam.esm.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type User details.
 */
public class UserDetailsImpl implements UserDetails {

    private long id;
    private String name;
    private String lastname;
    private String surname;
    private BigDecimal balance;
    private String email;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Instantiates a new User details.
     *
     * @param id          the id
     * @param name        the name
     * @param lastname    the lastname
     * @param surname     the surname
     * @param balance     the balance
     * @param email       the email
     * @param password    the password
     * @param authorities the authorities
     */
    public UserDetailsImpl(long id,
                           String name,
                           String lastname,
                           String surname,
                           BigDecimal balance,
                           String email,
                           String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.balance = balance;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Build user details.
     *
     * @param user the user
     * @return the user details
     */
    public static UserDetailsImpl build(User user){
        List<GrantedAuthority> authorities = user.getRoleList().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getLastname(),
                user.getSurname(),
                user.getBalance(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
