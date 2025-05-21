package dev.yunseong.beno2assignment.service;

import dev.yunseong.beno2assignment.domain.Schedule;
import dev.yunseong.beno2assignment.dto.ScheduleRequestDto;
import dev.yunseong.beno2assignment.dto.ScheduleResponseDto;
import dev.yunseong.beno2assignment.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.createSchedule(scheduleRequestDto.getTitle(), scheduleRequestDto.getContent(), scheduleRequestDto.getPassword(), scheduleRequestDto.getUserId());
        return new ScheduleResponseDto(schedule);
    }

    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.getSchedule(id);
        return new ScheduleResponseDto(schedule);
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        if (scheduleRepository.getPassword(id).equals(scheduleRequestDto.getPassword())) {
            Schedule schedule = scheduleRepository.updateSchedule(id, scheduleRequestDto.getContent());
            return new ScheduleResponseDto(schedule);
        } else {
            throw new IllegalArgumentException("Not Authorized");
        }
    }

    public void deleteSchedule(Long id, String password) {
        if (scheduleRepository.getPassword(id).equals(password)) {
            scheduleRepository.deleteSchedule(id);
        } else {
            throw new IllegalArgumentException("Not Authorized");
        }
    }

    public List<ScheduleResponseDto> getSchedules(Long writer, LocalDate updatedAt, int pageNo, int pageSize) {
        List<Schedule> schedules;
        if (writer == null && updatedAt == null) {
            schedules = scheduleRepository.getSchedules();
        } else if (writer != null && updatedAt == null) {
            schedules = scheduleRepository.getSchedulesByUserId(writer);
        } else if (writer == null && updatedAt != null) {
            schedules = scheduleRepository.getSchedulesByDate(Date.valueOf(updatedAt));
        } else {
            schedules = scheduleRepository.getSchedulesByUserIdAndDate(writer, Date.valueOf(updatedAt));
        }

        log.info("getSchedules: writer={}, updatedAt={}, pageNo={}, pageSize={}", writer, updatedAt, pageNo, pageSize);
        if (pageNo == 1 && pageSize == -1) {
            return schedules.stream()
                    .map(schedule -> new ScheduleResponseDto(schedule))
                    .collect(Collectors.toList());
        } else if (pageSize != -1) {
            return paging(schedules, pageNo, pageSize);
        } else if (pageNo != 1 && pageSize == -1) {
            return paging(schedules, pageNo, 24); // default page size is 24
        }
        return Collections.EMPTY_LIST;
    }

    public List<ScheduleResponseDto> paging(List<Schedule> schedules, int pageNo, int pageSize) {
        return schedules.stream()
                .map(schedule -> new ScheduleResponseDto(schedule))
                .skip((pageNo - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
