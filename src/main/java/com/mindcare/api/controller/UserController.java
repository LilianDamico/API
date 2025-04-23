package com.mindcare.api.controller;

import com.mindcare.api.dto.UserDTO;
import com.mindcare.api.model.User;
import com.mindcare.api.repository.UserRepository;
import com.mindcare.api.security.AuthUtil;
import com.mindcare.api.security.UserDetailsImpl;
import com.mindcare.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> cadastrar(@RequestBody UserDTO dto) {
        User novo = userService.criarUsuario(dto);
        return ResponseEntity.ok(novo);
    }

    @PreAuthorize("hasAnyAuthority('PROFISSIONAL', 'ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<User> getUsuarioLogado() {
        UserDetailsImpl authUser = AuthUtil.getUsuarioAutenticado();
        Optional<User> user = userRepository.findByEmail(authUser.getUsername());

        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
}
