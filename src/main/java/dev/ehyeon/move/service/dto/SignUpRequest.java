package dev.ehyeon.move.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignUpRequest {

    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

    @NotBlank
    private final String nickname;

    @NotNull
    private final int birthDate;

    @NotBlank
    private final String sex;

    @NotBlank
    private final String province;
}
