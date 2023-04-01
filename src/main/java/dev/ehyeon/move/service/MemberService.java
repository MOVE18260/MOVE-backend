package dev.ehyeon.move.service;

import dev.ehyeon.move.entity.Member;
import dev.ehyeon.move.repository.MemberRepository;
import dev.ehyeon.move.service.dto.SignInRequest;
import dev.ehyeon.move.service.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public boolean signIn(SignInRequest request) {
        return memberRepository.existsMemberByEmailAndPassword(request.getEmail(),
                request.getPassword());
    }

    @Transactional
    public Member signUp(SignUpRequest request) {
        if (memberRepository.existsMemberByEmail(request.getEmail())) {
            return null;
        }
        return memberRepository.save(
                new Member(request.getEmail(), request.getPassword(), request.getNickname(),
                        request.getBirthDate(), request.getProvince()));
    }

    @Transactional
    public Member getMemberByEmailAndPassword(SignInRequest request) {
        return memberRepository.findMemberByEmailAndPassword(request.getEmail(),
                        request.getPassword())
                .orElseThrow(IllegalArgumentException::new);
    }
}
