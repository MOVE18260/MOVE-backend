package dev.ehyeon.move.repository;

import static dev.ehyeon.move.entity.QRecord.record;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
                .where(eqMemberId(memberId).and(betweenDateTime(from, to)))
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
                .where(eqMemberId(memberId).and(betweenDateTime(from, to)))
                // TODO FIX postgres dayOfYear() 지원 안함, dayOfMonth()도 적합하지 않음
                .groupBy(record.recordId.dateTime.dayOfMonth())
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
                .where(eqMemberId(memberId).and(betweenDateTime(from, to)))
                .groupBy(record.recordId.dateTime.month())
                .fetch();
    }

    private BooleanExpression eqMemberId(Long memberId) {
        return record.recordId.memberId.eq(memberId);
    }

    private BooleanExpression betweenDateTime(LocalDateTime from, LocalDateTime to) {
        return record.recordId.dateTime.between(from, to);
    }
}
