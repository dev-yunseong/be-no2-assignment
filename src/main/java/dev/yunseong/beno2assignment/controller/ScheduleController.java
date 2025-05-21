package dev.yunseong.beno2assignment.controller;

import dev.yunseong.beno2assignment.dto.ScheduleRequestDto;
import dev.yunseong.beno2assignment.dto.ScheduleResponseDto;
import dev.yunseong.beno2assignment.dto.ScheduleUpdateRequestDto;
import dev.yunseong.beno2assignment.exception.AuthException;
import dev.yunseong.beno2assignment.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;


    @PostMapping("")
    public ScheduleResponseDto createSchedule(@Validated @RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @GetMapping("")
    public List<ScheduleResponseDto> getSchedules(
            @RequestParam(value="writer", required = false) Long writer,
            @RequestParam(value="modificatedDate", required = false) LocalDate updatedAt,
            @RequestParam(value="pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value="pageSize", defaultValue = "-1") int pageSize) {
        return scheduleService.getSchedules(writer, updatedAt, pageNo, pageSize);
    }

    @GetMapping("/{scheduleId}")
    public ScheduleResponseDto getSchedule(@PathVariable Long scheduleId) {
        return scheduleService.getSchedule(scheduleId);
    }

    @PutMapping("/{scheduleId}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long scheduleId, @Validated @RequestBody ScheduleUpdateRequestDto scheduleUpdateRequestDto) {
        return scheduleService.updateSchedule(scheduleId, scheduleUpdateRequestDto);
    }

    @DeleteMapping("/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId, @RequestBody String password) {
        scheduleService.deleteSchedule(scheduleId, password);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleAuthException(AuthException ex) {
        return new ResponseEntity("password is not correct", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity("request is not correct", HttpStatus.BAD_REQUEST);
    }
}
