package dev.ehyeon.move.service.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddPointRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private int point;
}
