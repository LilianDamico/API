package com.mindcare.api.controller;

import com.mindcare.api.dto.AppointmentDTO; // Importa o DTO que contém os dados da nova consulta
import com.mindcare.api.model.Appointment;
import com.mindcare.api.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importa HttpStatus para usar na resposta
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos") // Prefixo para todos os endpoints deste controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Endpoint para listar horários livres (se houver, e como ele busca esses horários)
    // O método no serviço precisa ser capaz de fornecer esses horários.
    // Se "listarHorariosLivres" no serviço realmente retorna "Appointments" que representam horários livres,
    // então a estrutura está ok, mas o nome pode ser confuso.
    @GetMapping("/horarios-livres")
    @PreAuthorize("hasAuthority('PACIENTE')") // Ou ajuste a autorização
    public List<Appointment> listarHorariosLivres() {
        // Você pode precisar passar parâmetros aqui (ex: data, profissionalId)
        // para filtrar os horários livres. Ajuste o service method se necessário.
        return appointmentService.listarHorariosLivres(); // <- Confirme que este método existe no service
    }

    // Endpoint para AGENDAR UMA NOVA CONSULTA
    // Agora ele recebe o AppointmentDTO, que contém pacienteId, profissionalId, dataHora e observacoes.
    @PostMapping
    @PreAuthorize("hasAuthority('PACIENTE')") // Somente pacientes podem agendar, ou ajuste conforme a regra
    public ResponseEntity<Appointment> agendarConsulta(@RequestBody AppointmentDTO dto) { // Recebe AppointmentDTO
        try {
            // Chama o método `agendarNovaConsulta` no serviço, passando o DTO completo.
            // Erro 1: Corrigido o nome do método de agendamento no service
            Appointment novaConsulta = appointmentService.agendarNovaConsulta(dto);
            return new ResponseEntity<>(novaConsulta, HttpStatus.CREATED); // Retorna 201 Created em caso de sucesso
        } catch (RuntimeException e) {
            // Se um RuntimeException for lançada (ex: paciente ou profissional não encontrado),
            // retorna um BAD_REQUEST (400) com a mensagem de erro.
            return ResponseEntity.badRequest().body(null); // Pode retornar a mensagem e.getMessage() aqui se quiser
        } catch (Exception e) {
            // Para outros erros inesperados, retorna um INTERNAL_SERVER_ERROR (500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint para desmarcar/cancelar uma consulta existente
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PACIENTE')") // Somente pacientes podem desmarcar, ou ajuste a regra
    public ResponseEntity<String> desmarcarConsulta(@PathVariable Long id) {
        try {
            // Erro 2: Corrigido o nome do método de desmarcar no service, ou adicione o método se ele não existir
            appointmentService.desmarcarConsulta(id); // Chama o método no serviço para desmarcar
            return ResponseEntity.ok("Consulta desmarcada com sucesso"); // Retorna 200 OK
        } catch (Exception e) {
            // Retorna BAD_REQUEST se houver algum problema ao desmarcar (ex: consulta não encontrada)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}