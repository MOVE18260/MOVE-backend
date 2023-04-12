package dev.ehyeon.move.repository;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RecordDto {

    private LocalDateTime dateTime;
    private int step;
    private int distance;
}
