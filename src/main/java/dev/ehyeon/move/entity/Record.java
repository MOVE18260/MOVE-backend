package dev.ehyeon.move.entity;

import java.time.LocalDateTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Record {

    @EmbeddedId
    private RecordId recordId;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String location;

    private int step;

    private int distance;

    public Record(Member member, LocalDateTime dateTime, String location, int step, int distance) {
        this.recordId = new RecordId(member.getId(), dateTime);
        setMember(member);
        this.location = location;
        this.step = step;
        this.distance = distance;
    }

    private void setMember(Member member) {
        this.member = member;
        member.getRecords().add(this);
    }
}
