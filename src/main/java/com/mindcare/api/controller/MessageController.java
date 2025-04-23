package com.mindcare.api.controller;

import com.mindcare.api.model.Message;
import com.mindcare.api.model.User;
import com.mindcare.api.service.MessageService;
import com.mindcare.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/usuarios")
    public List<User> listarContatos() {
        Long userId = userService.getIdUserLogado();
        return messageService.listarContatos(userId);
    }

    @GetMapping("/conversa/{id}")
    public List<Message> mensagensCom(@PathVariable Long id) {
        Long userId = userService.getIdUserLogado();
        return messageService.buscarMensagens(userId, id);
    }

    @PostMapping
    public Message enviarMensagem(@RequestBody EnvioMensagemDTO dto) {
        Long userId = userService.getIdUserLogado();
        return messageService.enviarMensagem(userId, dto.destinatarioId(), dto.content());
    }

    @PutMapping("/{id}/lida")
    public void marcarComoLida(@PathVariable Long id) {
        messageService.marcarComoLida(id);
    }

    public record EnvioMensagemDTO(Long destinatarioId, String content) {}
}
