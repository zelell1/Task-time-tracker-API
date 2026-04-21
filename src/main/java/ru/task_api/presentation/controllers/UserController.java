package ru.task_api.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.task_api.infrastructure.security.JwtService;
import ru.task_api.infrastructure.security.UserDetailServiceImpl;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailService;

    public UserController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserDetailServiceImpl userDetailService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        UserDetails userDetails = userDetailService.loadUserByUsername(request.username());
        return jwtService.generateToken(userDetails);
    }

    public record LoginRequest(String username, String password) {}
}
