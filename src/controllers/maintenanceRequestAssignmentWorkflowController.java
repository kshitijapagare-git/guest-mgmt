package <no value>.maintenance.request.assignment;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

import <no value>.maintenance.request.assignment.dto.MaintenanceRequestCreateDto;
import <no value>.maintenance.request.assignment.dto.MaintenanceRequestDto;
import <no value>.maintenance.request.assignment.controller.MaintenanceRequestAssignmentWorkflowController;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

// Note: This project uses generated controller-service-repository wiring.
// The controller export is required by the module layout contract.

import <no value>.maintenance.request.assignment.MaintenanceRequestAssignmentWorkflowService;

@RestController
@RequestMapping("/api")
public class MaintenanceRequestAssignmentWorkflowController {

    private final MaintenanceRequestAssignmentWorkflowService maintenanceRequestAssignmentWorkflowService;

    public MaintenanceRequestAssignmentWorkflowController(MaintenanceRequestAssignmentWorkflowService maintenanceRequestAssignmentWorkflowService) {
        this.maintenanceRequestAssignmentWorkflowService = maintenanceRequestAssignmentWorkflowService;
    }

    @PostMapping("/maintenance-requests")
    public ResponseEntity<?> createMaintenanceRequest(
            @RequestBody MaintenanceRequestCreateDto dto,
            Principal principal
    ) {
        return maintenanceRequestAssignmentWorkflowService.createMaintenanceRequest(dto, principal);
    }

    @PostMapping("/maintenance-requests/{requestId}/assign/{technicianId}")
    public ResponseEntity<?> assignTechnician(
            @PathVariable UUID requestId,
            @PathVariable UUID technicianId,
            Principal principal
    ) {
        return maintenanceRequestAssignmentWorkflowService.assignTechnician(requestId, technicianId, principal);
    }

    @PostMapping("/maintenance-requests/{requestId}/reassign/{technicianId}")
    public ResponseEntity<?> reassignTechnician(
            @PathVariable UUID requestId,
            @PathVariable UUID technicianId,
            Principal principal
    ) {
        return maintenanceRequestAssignmentWorkflowService.reassignTechnician(requestId, technicianId, principal);
    }

    @PostMapping("/maintenance-requests/{requestId}/start")
    public ResponseEntity<?> startMaintenance(
            @PathVariable UUID requestId,
            Principal principal
    ) {
        return maintenanceRequestAssignmentWorkflowService.startMaintenance(requestId, principal);
    }

    @PostMapping("/maintenance-requests/{requestId}/resolve")
    public ResponseEntity<?> resolveMaintenance(
            @PathVariable UUID requestId,
            @RequestBody MaintenanceRequestResolveDto dto,
            Principal principal
    ) {
        return maintenanceRequestAssignmentWorkflowService.resolveMaintenance(
                requestId,
                dto.getEstimatedCost(),
                dto.getResolvedAt(),
                principal
        );
    }

    @PostMapping("/maintenance-requests/{requestId}/close")
    public ResponseEntity<?> closeMaintenance(
            @PathVariable UUID requestId,
            Principal principal
    ) {
        return maintenanceRequestAssignmentWorkflowService.closeMaintenance(requestId, principal);
    }

    @GetMapping("/hotels/{hotelId}/maintenance-requests/search")
    public ResponseEntity<Page<MaintenanceRequestDto>> searchMaintenanceRequests(
            @RequestParam(required = false) String search,
            @PathVariable UUID hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Principal principal
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return maintenanceRequestAssignmentWorkflowService.searchMaintenanceRequests(search, hotelId, pageable, principal);
    }

    // Internal DTO used by resolve endpoint (kept local so other capabilities remain unaffected)
    public static class MaintenanceRequestResolveDto {
        private BigDecimal estimatedCost;
        private Instant resolvedAt;

        public BigDecimal getEstimatedCost() {
            return estimatedCost;
        }

        public void setEstimatedCost(BigDecimal estimatedCost) {
            this.estimatedCost = estimatedCost;
        }

        public Instant getResolvedAt() {
            return resolvedAt;
        }

        public void setResolvedAt(Instant resolvedAt) {
            this.resolvedAt = resolvedAt;
        }
    }
}
