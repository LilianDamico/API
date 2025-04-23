package com.mindcare.api.controller;

import com.mindcare.api.dto.RelatorioDTO;
import com.mindcare.api.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @GetMapping("/registro/{id}")
    public ResponseEntity<RelatorioDTO> gerarRelatorio(@PathVariable Long id) {
        return relatorioService.gerarRelatorioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('PROFISSIONAL')")
    @GetMapping("/registro/{id}/pdf")
    public ResponseEntity<byte[]> gerarRelatorioPdf(@PathVariable Long id) {
        return relatorioService.gerarPdfPorRegistro(id);
    }

}

