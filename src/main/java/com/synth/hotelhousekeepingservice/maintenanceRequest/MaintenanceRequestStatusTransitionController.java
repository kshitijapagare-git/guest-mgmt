package com.synth.hotelhousekeepingservice.maintenanceRequest;

import com.synth.hotelhousekeepingservice.exception.MaintenanceRequestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/maintenance-requests")
@Tag(name = "MaintenanceRequest Status Transitions")
@SecurityRequirement(name = "bearerAuth")
public class MaintenanceRequestStatusTransitionController {

    private final MaintenanceRequestRepository repository;

    @PostMapping("/{id}/assign")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Technician assigned to the request")
    @Transactional
    public MaintenanceRequestResponse assign(@PathVariable UUID id) {
        MaintenanceRequest entity = repository.findById(id)
                .orElseThrow(() -> new MaintenanceRequestNotFoundException(id));
        if (!entity.getStatus().equals("OPEN")) {
            throw new IllegalStateException(
                    "Status must be 'OPEN' to perform this transition, current is '" + entity.getStatus() + "'");
        }
        entity.setStatus("ASSIGNED");
        return MaintenanceRequestResponse.from(repository.save(entity));
    }

    @PostMapping("/{id}/start")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Technician starts work")
    @Transactional
    public MaintenanceRequestResponse start(@PathVariable UUID id) {
        MaintenanceRequest entity = repository.findById(id)
                .orElseThrow(() -> new MaintenanceRequestNotFoundException(id));
        if (!entity.getStatus().equals("ASSIGNED")) {
            throw new IllegalStateException(
                    "Status must be 'ASSIGNED' to perform this transition, current is '" + entity.getStatus() + "'");
        }
        entity.setStatus("IN_PROGRESS");
        return MaintenanceRequestResponse.from(repository.save(entity));
    }

    @PostMapping("/{id}/resolve")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Issue fixed")
    @Transactional
    public MaintenanceRequestResponse resolve(@PathVariable UUID id) {
        MaintenanceRequest entity = repository.findById(id)
                .orElseThrow(() -> new MaintenanceRequestNotFoundException(id));
        if (!entity.getStatus().equals("IN_PROGRESS")) {
            throw new IllegalStateException(
                    "Status must be 'IN_PROGRESS' to perform this transition, current is '" + entity.getStatus() + "'");
        }
        entity.setStatus("RESOLVED");
        return MaintenanceRequestResponse.from(repository.save(entity));
    }

    @PostMapping("/{id}/close")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Request closed after verification")
    @Transactional
    public MaintenanceRequestResponse close(@PathVariable UUID id) {
        MaintenanceRequest entity = repository.findById(id)
                .orElseThrow(() -> new MaintenanceRequestNotFoundException(id));
        if (!entity.getStatus().equals("RESOLVED")) {
            throw new IllegalStateException(
                    "Status must be 'RESOLVED' to perform this transition, current is '" + entity.getStatus() + "'");
        }
        entity.setStatus("CLOSED");
        return MaintenanceRequestResponse.from(repository.save(entity));
    }
}
