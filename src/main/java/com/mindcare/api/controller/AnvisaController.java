package com.mindcare.api.controller;

import com.mindcare.api.dto.MedicamentoInfoDTO;
import com.mindcare.api.service.AnvisaApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicamentos")
public class AnvisaController {

    @Autowired
    private AnvisaApiService anvisaService;

    @GetMapping("/{nome}")
    public ResponseEntity<MedicamentoInfoDTO> consultar(@PathVariable String nome) {
        MedicamentoInfoDTO info = anvisaService.consultar(nome);
        return ResponseEntity.ok(info);
    }
}

