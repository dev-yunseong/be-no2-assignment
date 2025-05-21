package dev.yunseong.beno2assignment.service;

import dev.yunseong.beno2assignment.domain.User;
import dev.yunseong.beno2assignment.dto.UserRequestDto;
import dev.yunseong.beno2assignment.dto.UserResponseDto;
import dev.yunseong.beno2assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userRepository.createUser(
                userRequestDto.getName(),
                userRequestDto.getEmail());
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }

    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.getUsers();
        return users.stream()
                .map(user -> new UserResponseDto(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }
}
