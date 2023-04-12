package dev.ehyeon.move.repository;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ehyeon.move.entity.Member;
import dev.ehyeon.move.entity.Record;
import dev.ehyeon.move.entity.RecordId;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@DataJpaTest
class RecordMemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RecordRepository recordRepository;

    @BeforeEach
    void before() {
        memberRepository.save(new Member("test1@gmail.com",
                "password",
                "nickname1",
                LocalDate.of(2023, 1, 1),
                "남자",
                "province"));
        memberRepository.save(new Member("test2@gmail.com",
                "password",
                "nickname2",
                LocalDate.of(2023, 1, 2),
                "여자",
                "province"));
    }

    @Test
    @DisplayName("기록 save, find 테스트")
    void setRecordByMemberAndFindRecordByRecordIdTest() throws NotFoundException {
        Member foundMember = memberRepository
                .findMemberByEmailAndPassword("test1@gmail.com", "password")
                .orElseThrow(NotFoundException::new);

        Record savedRecord = recordRepository
                .save(new Record(
                        foundMember,
                        LocalDateTime.of(2023, 1, 1, 0, 0),
                        "서울특별시",
                        1,
                        1));

        Record foundRecord = recordRepository.findRecordByRecordId(new RecordId(
                        foundMember.getId(),
                        LocalDateTime.of(2023, 1, 1, 0, 0)))
                .orElseThrow(NotFoundException::new);

        assertThat(foundRecord.getMember()).isEqualTo(foundMember);
        assertThat(foundRecord.getLocation()).isEqualTo("서울특별시");
        assertThat(foundRecord.getStep()).isEqualTo(1);
        assertThat(foundRecord.getDistance()).isEqualTo(1);
    }
}
