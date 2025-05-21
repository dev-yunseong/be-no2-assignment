package dev.yunseong.beno2assignment.dto;

import dev.yunseong.beno2assignment.domain.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String createdAt;
    private final String updatedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.createdAt = schedule.getCreatedAt().toString();
        this.updatedAt = schedule.getUpdatedAt().toString();
    }
}
