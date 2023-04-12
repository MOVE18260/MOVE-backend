package dev.ehyeon.move.repository;

import static dev.ehyeon.move.entity.QRecord.record;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

public class RecordRepositoryImpl implements RecordRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public RecordRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<RecordDto> findAllRecordByMemberIdAndDateTimeBetween(
            Long memberId, LocalDateTime from, LocalDateTime to) {
        return jpaQueryFactory
                .select(Projections.constructor(RecordDto.class,
                        record.recordId.dateTime,
                        record.step,
                        record.distance))
                .from(record)
                .where(record.recordId.memberId.eq(memberId)
                        .and(record.recordId.dateTime.between(from, to)))
                .fetch();
    }

    @Override
    public List<RecordDto> findAllDailyRecordTotalByMemberIdAndDateTimeBetween(
            Long memberId, LocalDateTime from, LocalDateTime to) {
        return jpaQueryFactory
                .select(Projections.constructor(RecordDto.class,
                        record.recordId.dateTime.min(),
                        record.step.sum(),
                        record.distance.sum()))
                .from(record)
                .where(record.recordId.memberId.eq(memberId)
                        .and(record.recordId.dateTime.between(from, to)))
                .groupBy(record.recordId.dateTime.dayOfYear())
                .fetch();
    }

    @Override
    public List<RecordDto> findAllMonthlyRecordTotalByMemberIdAndDateTimeBetween(Long memberId,
            LocalDateTime from, LocalDateTime to) {
        return jpaQueryFactory
                .select(Projections.constructor(RecordDto.class,
                        record.recordId.dateTime.min(),
                        record.step.sum(),
                        record.distance.sum()))
                .from(record)
                .where(record.recordId.memberId.eq(memberId)
                        .and(record.recordId.dateTime.between(from, to)))
                .groupBy(record.recordId.dateTime.month())
                .fetch();
    }
}
