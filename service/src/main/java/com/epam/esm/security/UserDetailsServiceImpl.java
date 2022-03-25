package com.epam.esm.security;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static com.epam.esm.util.LocalizedMessage.getMessageForLocale;

/**
 * The type User details service.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Instantiates a new User details service.
     *
     * @param userRepository the user repository
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ServiceException(getMessageForLocale("not.found.user.email") + email));
        return UserDetailsImpl.build(user);
    }
}
