package dev.ehyeon.move.service.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@Getter
public class SetRecordRequest {

    @NotNull
    private Long memberId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd`T`HH:mm")
    private LocalDateTime dateTime;

    @NotBlank
    private String location;

    private int step;

    private int distance;
}
