package dev.ehyeon.move.service.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SignInRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
