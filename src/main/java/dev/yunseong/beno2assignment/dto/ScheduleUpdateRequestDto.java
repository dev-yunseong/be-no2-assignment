package dev.yunseong.beno2assignment.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ScheduleUpdateRequestDto {
    @Nonnull
    @Size(min = 1, max = 200)
    private final String content;
    @Nonnull
    private final String password;
    @Nonnull
    private final String name;
}
