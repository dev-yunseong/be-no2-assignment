package dev.yunseong.beno2assignment.controller;

import dev.yunseong.beno2assignment.dto.UserRequestDto;
import dev.yunseong.beno2assignment.dto.UserResponseDto;
import dev.yunseong.beno2assignment.exception.NotFoundException;
import dev.yunseong.beno2assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<UserResponseDto> createUser(@Validated @RequestBody UserRequestDto userRequestDto) {
        log.info("createUser: {}", userRequestDto.toString());
        return new ResponseEntity<>(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity("request is not correct", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNonFoundException(NotFoundException ex) {
        return new ResponseEntity("schedule is not found", HttpStatus.NOT_FOUND);
    }
}
