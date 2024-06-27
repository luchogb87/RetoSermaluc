package com.luiscalixto.reto.controllers;

import com.luiscalixto.reto.models.dtos.ServiceResponse;
import com.luiscalixto.reto.models.dtos.UserRequestDto;
import com.luiscalixto.reto.models.dtos.UserResponseDto;
import com.luiscalixto.reto.services.UserService;
import com.luiscalixto.reto.utils.Constants;
import com.luiscalixto.reto.utils.PasswordValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final PasswordValidator passwordValidator;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> getAll() {
        try {
            List<UserResponseDto> users = service.findAll();

            if (users.isEmpty())
                return new ResponseEntity<>(ServiceResponse.noContent(UserResponseDto.builder().build()), HttpStatus.NO_CONTENT);

            return ResponseEntity.ok().body(ServiceResponse.ok(users));

        } catch (DataAccessException e) {
            return new ResponseEntity<>(ServiceResponse.internalError(e.getMostSpecificCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> getById(@PathVariable String id) {

        try {
            Optional<UserResponseDto> user = service.findById(id);

            return user.map(userResponseDto ->
                    ResponseEntity.ok().body(ServiceResponse.ok(userResponseDto))).orElseGet(() ->
                    new ResponseEntity<>(ServiceResponse.noContent(UserResponseDto.builder().build()), HttpStatus.NO_CONTENT));

        } catch (DataAccessException e) {
            return new ResponseEntity<>(ServiceResponse.internalError(e.getMostSpecificCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> save(@RequestBody @Valid UserRequestDto dto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .collect(Collectors.toList());
                return new ResponseEntity<>(ServiceResponse.general(Constants.MESSAGE_BAD_REQUEST, errorMessages), HttpStatus.BAD_REQUEST);
            }

            if(!isValidEmail(dto.getEmail())){
                return new ResponseEntity<>(ServiceResponse.general(Constants.MESSAGE_EMAIL_BAD_PATTERN, null), HttpStatus.BAD_REQUEST);
            }

            if(!passwordValidator.isValidPassword(dto.getPassword())){
                return new ResponseEntity<>(ServiceResponse.general(Constants.MESSAGE_PASSWORD_BAD_PATTERN, null), HttpStatus.BAD_REQUEST);
            }

            var response = service.save(dto);
            if (response.isEmpty())
                return new ResponseEntity<>(ServiceResponse.general(Constants.MESSAGE_EMAIL_EXIST, null), HttpStatus.GONE);

            return new ResponseEntity<>( ServiceResponse.ok(response),
                    HttpStatus.CREATED);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(ServiceResponse.internalError(e.getMostSpecificCause().getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public boolean isValidEmail(String email){

        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        if (email == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
