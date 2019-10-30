package com.ryulth.jwtexample.security.service;

import com.ryulth.jwtexample.security.dto.UserDto;
import com.ryulth.jwtexample.security.entity.User;
import com.ryulth.jwtexample.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class UserService {

    private static final Pattern emailPattern = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public boolean registerUser(UserDto userDto) {
        if (isDuplicateEmail(userDto.getEmail())) {
            log.warn("duplicateEmail");
            return false;
        }
        if (!emailPattern.matcher(userDto.getEmail()).matches()) {
            log.warn("EmailPattern Error");
            return false;
        }
        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .thumbImageUrl("tmp")
                .imageUrl("tmp")
                .build();

        userRepository.save(user);
        return true;
    }

    public boolean isDuplicateEmail(String userEmail) {
        return userRepository.existsByEmail(userEmail);
    }
}
