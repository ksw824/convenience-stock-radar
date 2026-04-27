package org.example.convradar.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATE_EMAIL(409, "이미 존재하는 이메일입니다."),

    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),

    INVALID_PASSWORD(400, "비밀번호가 올바르지 않습니다."),

    UNAUTHORIZED(401, "로그인이 필요합니다."),

    FORBIDDEN(403, "권한이 없습니다."),

    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int status;

    private final String message;
}
