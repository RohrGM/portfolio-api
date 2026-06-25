package com.rohr.portfolio_api.v1.controller;

import com.rohr.portfolio_api.v1.domain.dto.DashboardReportDTO;
import com.rohr.portfolio_api.v1.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "/report")
    public ResponseEntity<DashboardReportDTO> getReport() {
        return ResponseEntity.ok(this.dashboardService.getReport());
    }
}
