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
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint de cadastro
    @PostMapping
    public ResponseEntity<User> criarUsuario(@RequestBody UserDTO dto) {
        User novoUsuario = userService.criarUsuario(dto);
        return ResponseEntity.ok(novoUsuario);
    }

    // Endpoint para retornar usuário logado
    @GetMapping("/me")
    public ResponseEntity<User> getUsuarioLogado(@RequestHeader("Authorization") String token) {
        User usuario = userService.buscarUsuarioPorToken(token);
        return ResponseEntity.ok(usuario);
    }

    // ✅ Endpoint para retornar lista de profissionais
    @GetMapping("/profissionais")
    public ResponseEntity<List<User>> listarProfissionais() {
        List<User> profissionais = userService.listarProfissionais();
        return ResponseEntity.ok(profissionais);
    }
}
