package dev.ehyeon.move.service.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@Getter
public class GetRecordRequest {

    @NotNull
    private Long memberId;

    @NotNull
    @DateTimeFormat(pattern = "yyyyMMddHHmm")
    private LocalDateTime from;

    @DateTimeFormat(pattern = "yyyyMMddHHmm")
    private LocalDateTime to;
}
