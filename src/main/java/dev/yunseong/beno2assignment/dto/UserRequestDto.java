package dev.yunseong.beno2assignment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {
    private final String name;
    private final String email;
}
