package com.mindcare.api.controller;

import com.mindcare.api.dto.AuthResponse;
import com.mindcare.api.dto.LoginRequest;
import com.mindcare.api.dto.UserDTO;
import com.mindcare.api.security.JwtUtil;
import com.mindcare.api.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails.getUsername());

        UserDTO userDTO = new UserDTO(
                userDetails.getNome(),
                userDetails.getEmail(),
                null,
                userDetails.getTipo(),
                userDetails.getClinicId()
        );

        return ResponseEntity.ok(new AuthResponse(token, userDTO));
    }
}
