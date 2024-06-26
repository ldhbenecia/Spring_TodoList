package com.example.spring_todo.domain.user.controller;

import com.example.spring_todo.domain.user.dto.UserLoginRequestDto;
import com.example.spring_todo.domain.user.dto.UserLoginResponseDto;
import com.example.spring_todo.domain.user.dto.UserSignUpRequestDto;
import com.example.spring_todo.domain.user.dto.UserSignUpResponseDto;
import com.example.spring_todo.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<UserSignUpResponseDto> signUp(@RequestBody @Valid UserSignUpRequestDto requestDto) {
        UserSignUpResponseDto createUser = userService.createUser(requestDto);
        return ResponseEntity.ok(createUser);
    }

    @PostMapping("/api/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto loginRequestDto) {
        UserLoginResponseDto responseDto = userService.login(loginRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
