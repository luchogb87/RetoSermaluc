package com.luiscalixto.reto;
import com.luiscalixto.reto.models.Phone;
import com.luiscalixto.reto.models.Token;
import com.luiscalixto.reto.models.User;
import com.luiscalixto.reto.models.dtos.PhoneDto;
import com.luiscalixto.reto.models.dtos.UserRequestDto;
import com.luiscalixto.reto.models.dtos.UserResponseDto;
import com.luiscalixto.reto.repositories.PhoneRepository;
import com.luiscalixto.reto.repositories.TokenRepository;
import com.luiscalixto.reto.repositories.UserRepository;
import com.luiscalixto.reto.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        // Aquí puedes hacer cualquier configuración necesaria antes de cada prueba
    }

    @Test
    void testFindById() {
        // Aquí puedes configurar tus objetos simulados y llamar al método que estás probando
        when(userRepository.findById(any())).thenReturn(Optional.of(buildUser()));

        Optional<UserResponseDto> result = userService.findById("1");

        // Aquí puedes hacer tus aserciones
        assertTrue(result.isEmpty());

        // Verifica que los métodos de tus objetos simulados fueron llamados
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    void testFindAll() {
        // Similar al anterior, pero para el método findAll
        when(userRepository.findAll()).thenReturn(List.of());

        List<UserResponseDto> result = userService.findAll();

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }



    private Token buildToken() {
        return Token.builder()
                .id("1")
                .token(UUID.randomUUID().toString())
                .build();
    }
    private UserRequestDto buildUserRequestDto() {
        return UserRequestDto.builder()
                .name("Lucho")
                .email("lucho@correo.com")
                .password("misPassoword")
                .phones(List.of(buildPhoneDto()))
                .build();
    }

    private PhoneDto buildPhoneDto() {
        return PhoneDto.builder()
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();
    }

    private List<Phone> buildPhones() {
        return List.of(Phone.builder()
                .id("1")
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build());
    }

    private UserResponseDto buildUserResponseDto() {
        return UserResponseDto.builder()
                .id("1")
                .created("2021-09-01T00:00:00")
                .modified("2021-09-01T00:00:00")
                .lastLogin("2021-09-01T00:00:00")
                .token(UUID.randomUUID().toString())
                .isActive(true)
                .build();
    }

    private User buildUser() {
        return User.builder()
                .id("1")
                .name("name")
                .email("email")
                .password("password")
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token("token")
                .isActive(true)
                .build();
    }
}