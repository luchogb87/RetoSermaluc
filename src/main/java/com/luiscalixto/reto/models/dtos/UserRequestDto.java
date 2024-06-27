package com.luiscalixto.reto.models.dtos;


import com.luiscalixto.reto.models.dtos.PhoneDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserRequestDto {
                private String id;
                private String name;
                @NotBlank
                private String email;
                @NotBlank
                private String password;
                private List<PhoneDto> phones;
}
