package com.yostoya.shoptill.service.impl;

import com.yostoya.shoptill.domain.User;
import com.yostoya.shoptill.exception.notfound.UserNotFoundException;
import com.yostoya.shoptill.repository.UserRepository;
import com.yostoya.shoptill.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UserNotFoundException("email", email);
        }

        return new UserPrincipal(user.get());
    }
}
