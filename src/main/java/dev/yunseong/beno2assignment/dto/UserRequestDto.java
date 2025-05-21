package dev.yunseong.beno2assignment.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {
    @Nonnull
    private final String name;

    @Nonnull
    @Email
    private final String email;
}
