package dev.ehyeon.move.controller;

import dev.ehyeon.move.controller.dto.MemberResponse;
import dev.ehyeon.move.entity.Member;
import dev.ehyeon.move.service.MemberService;
import dev.ehyeon.move.service.dto.SignInRequest;
import dev.ehyeon.move.service.dto.SignUpRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signIn")
    public boolean signIn(@Valid @RequestBody SignInRequest request) {
        return memberService.signIn(request);
    }

    @PostMapping("/signUp")
    public ResponseEntity<MemberResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        Member foundMember = memberService.signUp(request);

        if (foundMember == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                new MemberResponse(foundMember.getNickname(), foundMember.getBirthDate(),
                        foundMember.getProvince()));
    }

    @PostMapping("/member")
    public ResponseEntity<MemberResponse> getMember(@Valid @RequestBody SignInRequest request) {
        try {
            Member foundMember = memberService.getMemberByEmailAndPassword(request);
            return ResponseEntity.ok(new MemberResponse(foundMember.getNickname(),
                    foundMember.getBirthDate(), foundMember.getProvince()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
