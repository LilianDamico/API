package com.mindcare.api.repository;

import com.mindcare.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    long countByTipo(String tipo); // Conta quantos usuários são do tipo especificado (ex: "PROFISSIONAL")
}
