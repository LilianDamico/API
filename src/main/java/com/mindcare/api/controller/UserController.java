package com.mindcare.api.controller;

import com.mindcare.api.dto.UserDTO;
import com.mindcare.api.model.User;
import com.mindcare.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> criarUsuario(@RequestBody UserDTO dto) {
        User novoUsuario = userService.criarUsuario(dto);
        return ResponseEntity.ok(novoUsuario);
    }

    @GetMapping("/profissionais")
    public ResponseEntity<List<User>> listarProfissionais() {
        List<User> profissionais = userService.listarProfissionais();
        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUsuarioLogado(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.buscarUsuarioPorToken(token));
    }
}
