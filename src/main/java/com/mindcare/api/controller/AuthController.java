package com.mindcare.api.controller;

import com.mindcare.api.dto.AuthResponse;
import com.mindcare.api.dto.LoginRequest;
import com.mindcare.api.dto.UserDTO;
import com.mindcare.api.security.JwtUtil;
import com.mindcare.api.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email, request.senha)
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String token = jwtUtil.generateToken(userDetails.getUsername());

    // Aqui você cria um DTO com os dados do usuário logado
    UserDTO userDTO = new UserDTO(
    userDetails.getNome(),
    userDetails.getEmail(),
    "protegida", // ou null, já que a senha não deve ser exposta
    userDetails.getTipo(),
    null // ou userDetails.getClinicId() se houver esse campo
);



    return ResponseEntity.ok(new AuthResponse(token, userDTO));
}

}
