package dev.ehyeon.move.repository;

import dev.ehyeon.move.entity.Record;
import dev.ehyeon.move.entity.RecordId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, RecordId> {

    Optional<Record> findRecordByRecordId(RecordId recordId);
}
