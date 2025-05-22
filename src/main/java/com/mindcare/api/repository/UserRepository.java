package com.mindcare.api.repository;

import com.mindcare.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByTipo(String tipo);

    // ✅ Adicionado
    Long countByTipo(String tipo);
}
