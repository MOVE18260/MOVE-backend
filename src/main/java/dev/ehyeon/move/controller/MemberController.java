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
    public ResponseEntity<Long> signIn(@Valid @RequestBody SignInRequest request) {
        try {
            return ResponseEntity.ok(memberService.signIn(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<Long> signUp(@Valid @RequestBody SignUpRequest request) {
        try {
            return ResponseEntity.ok(memberService.signUp(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/member")
    public ResponseEntity<MemberResponse> getMember(@RequestParam("memberId") long memberId) {
        try {
            Member foundMember = memberService.getMemberById(memberId);

            return ResponseEntity.ok(
                    new MemberResponse(
                            foundMember.getNickname(),
                            foundMember.getBirthDate().getYear(),
                            foundMember.getSex(),
                            foundMember.getProvince(),
                            foundMember.getPoint()
                    )
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
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
