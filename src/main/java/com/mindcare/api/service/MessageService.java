package com.mindcare.api.service;

import com.mindcare.api.model.Message;
import com.mindcare.api.model.User;
import com.mindcare.api.repository.MessageRepository;
import com.mindcare.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> listarContatos(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return messageRepository.findConversasPorUsuario(user);
    }

    public List<Message> buscarMensagens(Long userId, Long outroId) {
        User user = userRepository.findById(userId).orElseThrow();
        User outro = userRepository.findById(outroId).orElseThrow();
        return messageRepository.findMensagensEntre(user, outro);
    }

    public Message enviarMensagem(Long remetenteId, Long destinatarioId, String content) {
        User remetente = userRepository.findById(remetenteId).orElseThrow();
        User destinatario = userRepository.findById(destinatarioId).orElseThrow();

        Message mensagem = new Message();
        mensagem.setRemetente(remetente);
        mensagem.setDestinatario(destinatario);
        mensagem.setContent(content);
        return messageRepository.save(mensagem);
    }

    public void marcarComoLida(Long id) {
        Message msg = messageRepository.findById(id).orElseThrow();
        msg.setLida(true);
        messageRepository.save(msg);
    }
}
