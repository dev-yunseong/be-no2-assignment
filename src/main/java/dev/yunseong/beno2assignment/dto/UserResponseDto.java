package dev.yunseong.beno2assignment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final String email;
}
