package dev.ehyeon.move.controller;

import dev.ehyeon.move.controller.dto.MemberResponse;
import dev.ehyeon.move.entity.Member;
import dev.ehyeon.move.service.MemberService;
import dev.ehyeon.move.service.dto.AddPointRequest;
import dev.ehyeon.move.service.dto.SignInRequest;
import dev.ehyeon.move.service.dto.SignUpRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signIn")
    public long signIn(@Valid @RequestBody SignInRequest request) {
        return memberService.signIn(request);
    }

    @PostMapping("/signUp")
    public long signUp(@Valid @RequestBody SignUpRequest request) {
        return memberService.signUp(request);
    }

    @GetMapping("/member")
    public ResponseEntity<MemberResponse> getMember(@RequestParam("memberId") long memberId) {
        Member foundMember = memberService.getMemberById(memberId);

        System.out.println("들어옴");

        return ResponseEntity.ok(
                new MemberResponse(
                        foundMember.getNickname(),
                        foundMember.getBirthDate().getYear(),
                        foundMember.getSex(),
                        foundMember.getProvince(),
                        foundMember.getPoint()
                )
        );
    }

    @PostMapping("/member/point")
    public boolean addMemberPoint(@Valid @RequestBody AddPointRequest request) {
        try {
            return memberService.addPoint(request);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
