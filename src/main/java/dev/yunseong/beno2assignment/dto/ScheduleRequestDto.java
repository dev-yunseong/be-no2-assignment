package dev.yunseong.beno2assignment.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleRequestDto {
    @Nonnull
    private final String title;
    @Nonnull
    @Size(min = 1, max = 200)
    private final String content;
    @Nonnull
    private final String password;
    @Nonnull
    private final Long userId;
}

