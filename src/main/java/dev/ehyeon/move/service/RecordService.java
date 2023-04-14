package dev.ehyeon.move.service;

import dev.ehyeon.move.entity.Member;
import dev.ehyeon.move.entity.Record;
import dev.ehyeon.move.repository.MemberRepository;
import dev.ehyeon.move.repository.RecordDto;
import dev.ehyeon.move.repository.RecordRepository;
import dev.ehyeon.move.service.dto.GetRecordRequest;
import dev.ehyeon.move.service.dto.SetRecordRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    private final MemberRepository memberRepository;

    // TODO 중복 문제 발견
    @Transactional
    public RecordDto setRecord(SetRecordRequest request) throws NotFoundException {
        Member foundMember = memberRepository.findById(request.getMemberId())
                .orElseThrow(NotFoundException::new);

        Record savedRecord = recordRepository.save(new Record(
                foundMember, request.getDateTime(), request.getLocation(), request.getStep(),
                request.getDistance()
        ));

        return new RecordDto(savedRecord.getRecordId().getDateTime(),
                savedRecord.getStep(),
                savedRecord.getDistance());
    }

    public List<RecordDto> getRecord(GetRecordRequest request) {
        return recordRepository.findAllRecordByMemberIdAndDateTimeBetween(
                request.getMemberId(),
                request.getFrom(),
                request.getTo());
    }

    public List<RecordDto> getRecordTotal(GetRecordRequest request) {
        return recordRepository.findAllDailyRecordTotalByMemberIdAndDateTimeBetween(
                request.getMemberId(),
                request.getFrom(),
                request.getTo());
    }
}
