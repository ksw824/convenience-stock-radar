package org.example.convradar.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.convradar.domain.member.dto.MemberJoinRequest;
import org.example.convradar.domain.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody MemberJoinRequest request) {
        return ResponseEntity.ok(memberService.join(request));
    }
}
