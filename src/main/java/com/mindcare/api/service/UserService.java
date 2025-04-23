package com.mindcare.api.service;

import com.mindcare.api.dto.UserDTO;
import com.mindcare.api.model.Clinic;
import com.mindcare.api.model.User;
import com.mindcare.api.repository.ClinicRepository;
import com.mindcare.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public User criarUsuario(UserDTO dto) {
        User user = new User();
        user.setNome(dto.nome);
        user.setEmail(dto.email);
        user.setSenha(encoder.encode(dto.senha));
        user.setTipo(dto.tipo);

        if (dto.clinicId != null) {
            Optional<Clinic> clinicOpt = clinicRepository.findById(dto.clinicId);
            clinicOpt.ifPresent(user::setClinic);
        }

        return userRepository.save(user);
    }
}
