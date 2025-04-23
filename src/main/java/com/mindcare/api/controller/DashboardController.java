package com.mindcare.api.controller;

import com.mindcare.api.dto.DashboardDTO;
import com.mindcare.api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'PROFISSIONAL')")
    @GetMapping("/estatisticas")
    public ResponseEntity<DashboardDTO> obterEstatisticas() {
        DashboardDTO dto = dashboardService.getEstatisticas();
        return ResponseEntity.ok(dto);
    }
}
