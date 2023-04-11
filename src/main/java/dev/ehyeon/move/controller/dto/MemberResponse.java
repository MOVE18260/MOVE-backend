package dev.ehyeon.move.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberResponse {

    @NotBlank
    private final String nickname;

    @NotNull
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;

    @NotBlank
    private final String sex;

    @NotBlank
    private final String province;
}
