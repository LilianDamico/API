package com.mindcare.api.repository;

import com.mindcare.api.model.Message;
import com.mindcare.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT DISTINCT m.destinatario FROM Message m WHERE m.remetente = :user " +
           "UNION " +
           "SELECT DISTINCT m.remetente FROM Message m WHERE m.destinatario = :user")
    List<User> findConversasPorUsuario(User user);

    @Query("SELECT m FROM Message m WHERE " +
           "(m.remetente = :user1 AND m.destinatario = :user2) OR " +
           "(m.remetente = :user2 AND m.destinatario = :user1) " +
           "ORDER BY m.timestamp")
    List<Message> findMensagensEntre(User user1, User user2);
}
