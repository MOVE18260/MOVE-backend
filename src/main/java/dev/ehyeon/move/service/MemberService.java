package dev.ehyeon.move.service;

import dev.ehyeon.move.entity.Member;
import dev.ehyeon.move.repository.MemberRepository;
import dev.ehyeon.move.service.dto.SignInRequest;
import dev.ehyeon.move.service.dto.SignUpRequest;
import dev.ehyeon.move.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public long signIn(SignInRequest request) {
        return memberRepository
                .findMemberByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(IllegalArgumentException::new)
                .getId();
    }

    @Transactional
    public long signUp(SignUpRequest request) {
        if (memberRepository.existsMemberByEmail(request.getEmail())) {
            throw new IllegalArgumentException();
        }

        Member savedMember = memberRepository.save(
                new Member(request.getEmail(), request.getPassword(), request.getNickname(),
                        DateUtil.yearToLocalDateTime(request.getBirthDate()), request.getSex(),
                        request.getProvince()));

        return savedMember.getId();
    }

    public Member getMemberByEmailAndPassword(SignInRequest request) {
        return memberRepository.findMemberByEmailAndPassword(request.getEmail(),
                        request.getPassword())
                .orElseThrow(IllegalArgumentException::new);
    }
}
