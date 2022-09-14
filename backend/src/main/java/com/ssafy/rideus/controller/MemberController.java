package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.security.auth.CustomUserDetails;
import com.ssafy.rideus.dto.member.request.MemberMoreInfoReq;
import com.ssafy.rideus.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/check")
    public int nicknameCheck(String nickname) {

        return 0;
    }


    @PostMapping("/info")
    public void moreInformation(MemberMoreInfoReq memberMoreInfoReq, @ApiIgnore @AuthenticationPrincipal CustomUserDetails member) {
        memberService.moreInformation(memberMoreInfoReq, member.getId());

    }

    @GetMapping("/info")
    public int isGotName() {

        return 0;
    }
}
