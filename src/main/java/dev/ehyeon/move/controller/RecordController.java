package dev.ehyeon.move.controller;

import dev.ehyeon.move.repository.RecordDto;
import dev.ehyeon.move.service.RecordService;
import dev.ehyeon.move.service.dto.GetRecordRequest;
import dev.ehyeon.move.service.dto.SetRecordRequest;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping("/record")
    public RecordDto setRecord(@Valid @RequestBody SetRecordRequest request)
            throws NotFoundException {
        return recordService.setRecord(request);
    }

    @GetMapping("/record")
    public List<RecordDto> getRecord(@Valid @ModelAttribute GetRecordRequest request) {
        return recordService.getRecord(request);
    }

    @GetMapping("/recordTotal")
    public List<RecordDto> getRecordTotal(@Valid @ModelAttribute GetRecordRequest request) {
        return recordService.getRecordTotal(request);
    }
}
