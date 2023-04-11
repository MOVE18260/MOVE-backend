package dev.ehyeon.move.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberResponse {

    private final String nickname;

    private final int birthDate;

    private final String sex;

    private final String province;
}
