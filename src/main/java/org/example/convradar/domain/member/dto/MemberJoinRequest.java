package org.example.convradar.domain.member.dto;

import lombok.Getter;
import org.example.convradar.domain.member.entity.Role;

@Getter
public class MemberJoinRequest {
    private String email;
    private String password;
    private String name;
    private Role role;
}
