package dev.ehyeon.move.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class RecordId implements Serializable {

    private Long memberId;
    private LocalDateTime dateTime;

    public RecordId(Long memberId, LocalDateTime dateTime) {
        this.memberId = memberId;
        this.dateTime = dateTime;
    }
}
