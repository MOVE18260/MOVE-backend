package dev.ehyeon.move.repository;

import dev.ehyeon.move.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByEmail(String email);

    boolean existsMemberByEmailAndPassword(String email, String password);

    Optional<Member> findMemberByEmailAndPassword(String email, String password);
}
