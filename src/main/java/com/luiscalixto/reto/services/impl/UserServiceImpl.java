package com.luiscalixto.reto.services.impl;


import com.luiscalixto.reto.models.Phone;
import com.luiscalixto.reto.models.Token;
import com.luiscalixto.reto.models.User;
import com.luiscalixto.reto.models.dtos.PhoneDto;
import com.luiscalixto.reto.models.dtos.UserRequestDto;
import com.luiscalixto.reto.models.dtos.UserResponseDto;
import com.luiscalixto.reto.models.enums.TokenType;
import com.luiscalixto.reto.repositories.PhoneRepository;
import com.luiscalixto.reto.repositories.TokenRepository;
import com.luiscalixto.reto.repositories.UserRepository;
import com.luiscalixto.reto.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final TokenRepository tokenRepository;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository,
                           PhoneRepository phoneRepository,
                           TokenRepository tokenRepository,
                           ModelMapper mapperMock) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.tokenRepository = tokenRepository;
        this.mapper = mapperMock;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDto> findById(String id) {
        return userRepository.findById(id)
                .map( user -> mapper.map(user, UserResponseDto.class));

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                .map( user -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<UserResponseDto> save(UserRequestDto requestDto) {

        User user = User.builder()
        .name(requestDto.getName())
        .email(requestDto.getEmail())
        .password(requestDto.getPassword())
        .build();

        user.setInitialValues();


        var jwtToken = UUID.randomUUID().toString();
        if (!validateEmail(user.getEmail())){
            return Optional.empty();
        }
        user.setToken(jwtToken);
        User userResult = userRepository.save(user);

        registerPhones(userResult, requestDto.getPhones());
        saveUserToken(userResult, jwtToken);

        return Optional.of(mapper.map(userResult, UserResponseDto.class));

    }


    private void registerPhones(User user, List<PhoneDto> phones) {
        phoneRepository.saveAll(phones.stream()
            .map(dto -> {
                Phone phone = mapper.map(dto, Phone.class);
                phone.setUser(user);
                return phone;
            })
            .collect(Collectors.toList()));
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.UUID)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
    private Boolean validateEmail(String email){
        return userRepository.findByEmail(email).isEmpty();
    }
}
