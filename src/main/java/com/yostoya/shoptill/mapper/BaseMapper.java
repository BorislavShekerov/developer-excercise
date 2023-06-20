package com.yostoya.shoptill.mapper;


import com.yostoya.shoptill.domain.User;
import com.yostoya.shoptill.exception.alreadyexist.UserAlreadyExistException;
import com.yostoya.shoptill.repository.UserRepository;
import jakarta.validation.Path;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Condition;
import org.mapstruct.MapperConfig;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@MapperConfig
@RequiredArgsConstructor
public class BaseMapper {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Named("encodePassword")
    public String encodePassword(String raw) {
        return passwordEncoder.encode(raw);
    }

    @Named("notRegistered")
    @Condition
    private boolean notRegistered(String email) {
        final Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            throw new UserAlreadyExistException("email", email);
        }

        return true;
    }

    @Named("pathToString")
    public String pathToString(Path path) {
        return path.toString();
    }

}
