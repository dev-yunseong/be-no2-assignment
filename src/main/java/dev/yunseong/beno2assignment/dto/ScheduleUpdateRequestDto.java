package dev.yunseong.beno2assignment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ScheduleUpdateRequestDto {
    private final String content;
    private final String password;
    private final String name;
}
