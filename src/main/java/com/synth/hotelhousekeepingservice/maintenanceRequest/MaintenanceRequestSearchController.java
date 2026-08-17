package com.synth.hotelhousekeepingservice.maintenanceRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/maintenance-requests")
@Tag(name = "MaintenanceRequest Search")
public class MaintenanceRequestSearchController {

    private final MaintenanceRequestRepository repository;

    @Transactional(readOnly = true)
    @GetMapping("/search-maintenance-requests")
    @Operation(summary = "Search maintenance requests by description")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR','STAFF')")
    public List<MaintenanceRequestResponse> searchMaintenanceRequests(
            @RequestParam(required = false) String search) {
        if (search == null || search.isBlank()) return List.of();
        return repository.searchMaintenanceRequests(search).stream()
                .map(MaintenanceRequestResponse::from)
                .toList();
    }
}
