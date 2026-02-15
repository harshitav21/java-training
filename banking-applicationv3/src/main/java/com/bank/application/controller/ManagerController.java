package com.bank.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.dto.common.ManagerDashboardDto;
import com.bank.application.service.ManagerDashboardService;

@RestController
@RequestMapping("/api/manager")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {

    private final ManagerDashboardService dashboardService;

    public ManagerController(ManagerDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ManagerDashboardDto> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }
}