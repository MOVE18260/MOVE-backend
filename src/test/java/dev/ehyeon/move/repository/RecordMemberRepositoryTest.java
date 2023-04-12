package dev.ehyeon.move.repository;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ehyeon.move.entity.Member;
import dev.ehyeon.move.entity.Record;
import dev.ehyeon.move.entity.RecordId;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
        Member member1 = memberRepository.save(new Member("test1@gmail.com",
                "password",
                "nickname1",
                LocalDate.of(2023, 1, 1),
                "남자",
                "province"));
        Member member2 = memberRepository.save(new Member("test2@gmail.com",
                "password",
                "nickname2",
                LocalDate.of(2023, 1, 2),
                "여자",
                "province"));

        // 2023년 1월 1일 데이터 추가
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 1, 0, 30),
                        "location A", 100, 100));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 1, 1, 0),
                        "location A", 200, 200));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 1, 2, 0),
                        "location A", 300, 300));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 1, 2, 30),
                        "location A", 400, 400));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 1, 3, 0),
                        "location A", 500, 500));

        // 2023년 1월 2일 ~ 8일 데이터 추가
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 2, 1, 30),
                        "location A", 1000, 1000));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 2, 2, 0),
                        "location A", 1, 1)); // 합 조회용으로 1개 더 추가
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 3, 1, 30),
                        "location B", 2000, 2000));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 4, 1, 30),
                        "location C", 3000, 3000));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 5, 1, 30),
                        "location D", 4000, 4000));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 6, 1, 30),
                        "location E", 5000, 5000));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 7, 1, 30),
                        "location F", 6000, 6000));
        recordRepository.save(
                new Record(member1, LocalDateTime.of(2023, 1, 8, 1, 30),
                        "location G", 7000, 7000));

        // 월별 데이터 1개씩 추가
        for (int i = 2; i <= 12; i++) {
            recordRepository.save(
                    new Record(member2, LocalDateTime.of(2023, i, 1, 0, 0),
                            "location B", i + 2, i + 2));
        }
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

    @Nested
    @DisplayName("기록 조회")
    class FindAllRecord {

        @Test
        @DisplayName("일간 기록 조회")
        void findDailyRecordByMemberIdAndDateTimeTest() throws NotFoundException {
            // given
            LocalDateTime findDate = LocalDateTime.of(2023, 1, 1, 0, 0); // from
            LocalDateTime to = LocalDateTime.of(findDate.getYear(), findDate.getMonth(),
                    findDate.getDayOfMonth(), 23, 59, 59, 999999999);

            // when
            Member foundMember = memberRepository.findById(1L)
                    .orElseThrow(NotFoundException::new);

            List<RecordDto> result = recordRepository.findAllRecordByMemberIdAndDateTimeBetween(
                    foundMember.getId(), findDate, to);

            // then
            for (RecordDto recordDto : result) {
                System.out.println("recordDto = " + recordDto);
            }
        }

        @Test
        @DisplayName("주간 기록 조회")
        void findWeeklyRecordByMemberIdAndDateTimeTest() throws NotFoundException {
            // given
            LocalDateTime findDate = LocalDateTime.of(2023, 1, 2, 0, 0); // from, 1월 2일 = 월요일
            LocalDateTime to = findDate.plus(Period.ofDays(6)).plusHours(23).plusMinutes(59)
                    .plusSeconds(59).plusNanos(999999999);

            // when
            Member foundMember = memberRepository.findById(1L)
                    .orElseThrow(NotFoundException::new);

            List<RecordDto> result = recordRepository.findAllRecordByMemberIdAndDateTimeBetween(
                    foundMember.getId(), findDate, to);

            // then
            for (RecordDto recordDto : result) {
                System.out.println("recordDto = " + recordDto);
            }
        }
    }

    @Nested
    @DisplayName("기록 합 조회")
    class FindAllRecordTotal {

        @Test
        @DisplayName("기록 합 조회 - 1월 1일")
        void findRecordTotalOnJan1stTest() throws NotFoundException {
            // given
            LocalDateTime findDate = LocalDateTime.of(2023, 1, 1, 0, 0); // from
            LocalDateTime to = LocalDateTime.of(findDate.getYear(), findDate.getMonth(),
                    findDate.getDayOfMonth(), 23, 59, 59, 999999999);

            // when
            Member foundMember = memberRepository.findById(1L)
                    .orElseThrow(NotFoundException::new);

            List<RecordDto> result = recordRepository.findAllDailyRecordTotalByMemberIdAndDateTimeBetween(
                    foundMember.getId(), findDate, to);

            // then
            System.out.println("result = " + result.get(0));
        }

        @Test
        @DisplayName("일별 기록 합 조회 - 1월 2일 ~ 1월 8일")
        void findRecordTotalFromJan2To8Test() throws NotFoundException {
            // given
            LocalDateTime findDate = LocalDateTime.of(2023, 1, 2, 0, 0); // from, 1월 2일 = 월요일
            LocalDateTime to = findDate.plus(Period.ofDays(6)).plusHours(23).plusMinutes(59)
                    .plusSeconds(59).plusNanos(999999999);

            // when
            Member foundMember = memberRepository.findById(1L)
                    .orElseThrow(NotFoundException::new);

            List<RecordDto> result = recordRepository.findAllDailyRecordTotalByMemberIdAndDateTimeBetween(
                    foundMember.getId(), findDate, to);

            // then
            for (RecordDto recordDto : result) {
                System.out.println("recordDto = " + recordDto);
            }
        }

        @Test
        @DisplayName("월별 기록 합 조회 - 1월 ~ 12월")
        void findRecordTotalFromJanToDecTest() throws NotFoundException {
            // given
            LocalDateTime findDate = LocalDateTime.of(2023, 1, 1, 0, 0); // from
            LocalDateTime to = findDate.plus(Period.ofMonths(11)).plusHours(23).plusMinutes(59)
                    .plusSeconds(59).plusNanos(999999999);

            // when
            Member foundMember = memberRepository.findById(2L)
                    .orElseThrow(NotFoundException::new);

            List<RecordDto> result = recordRepository.findAllMonthlyRecordTotalByMemberIdAndDateTimeBetween(
                    foundMember.getId(), findDate, to);

            // then
            for (RecordDto recordDto : result) {
                System.out.println("recordDto = " + recordDto);
            }
        }

        /* THINK
         * 동적 쿼리 생각
         * 1. 기록 조회, 기록 합 조회 선택
         * 2. 일별, 월별, 연도별 선택
         * 3. from(날짜) 선택
         */
    }
}
