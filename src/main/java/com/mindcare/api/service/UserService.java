package com.mindcare.api.service;

import com.mindcare.api.dto.UserDTO;
import com.mindcare.api.model.Clinic;
import com.mindcare.api.model.User;
import com.mindcare.api.repository.ClinicRepository;
import com.mindcare.api.repository.UserRepository;
import com.mindcare.api.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public User criarUsuario(UserDTO dto) {
        User user = new User();

        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setTipo(dto.getTipo());

        if (dto.getClinicId() != null) {
            Optional<Clinic> clinicOpt = clinicRepository.findById(dto.getClinicId());
            clinicOpt.ifPresent(user::setClinic);
        }

        return userRepository.save(user);
    }

    public Long getIdUserLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;

        if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        } else {
            email = principal.toString();
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"))
                .getId();
    }

    public List<User> listarProfissionais() {
        return userRepository.findByTipo("PROFISSIONAL");
    }

    public User buscarUsuarioPorToken(String token) {
        String email = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}