package com.luiscalixto.reto.services;


import com.luiscalixto.reto.models.Phone;
import com.luiscalixto.reto.models.dtos.UserRequestDto;
import com.luiscalixto.reto.models.dtos.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponseDto> save(UserRequestDto requestDto);

    Optional<UserResponseDto> findById(String id);
    List<UserResponseDto> findAll();
}
