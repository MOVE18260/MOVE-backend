package dev.ehyeon.move.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepositoryCustom {

    List<RecordDto> findAllRecordByMemberIdAndDateTimeBetween(
            Long memberId, LocalDateTime from, LocalDateTime to);

    List<RecordDto> findAllDailyRecordTotalByMemberIdAndDateTimeBetween(
            Long memberId, LocalDateTime from, LocalDateTime to);

    List<RecordDto> findAllMonthlyRecordTotalByMemberIdAndDateTimeBetween(
            Long memberId, LocalDateTime from, LocalDateTime to);
}
