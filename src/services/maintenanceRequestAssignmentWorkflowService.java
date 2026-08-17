package <no value>.maintenance.request.assignment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import <no value>.maintenance.request.assignment.dto.MaintenanceRequestCreateDto;
import <no value>.maintenance.request.assignment.dto.MaintenanceRequestDto;

@Service
public class MaintenanceRequestAssignmentWorkflowService {

    private final MaintenanceRequestAssignmentWorkflowRepository maintenanceRequestAssignmentWorkflowRepository;

    public MaintenanceRequestAssignmentWorkflowService(MaintenanceRequestAssignmentWorkflowRepository maintenanceRequestAssignmentWorkflowRepository) {
        this.maintenanceRequestAssignmentWorkflowRepository = maintenanceRequestAssignmentWorkflowRepository;
    }

    public ResponseEntity<?> createMaintenanceRequest(MaintenanceRequestCreateDto dto, Principal principal) {
        if (dto == null) return ResponseEntity.badRequest().body("dto_required");
        // Business identity: hotelId is the resource scope; reportedByGuestId optional.
        UUID hotelId = dto.getHotelId();
        if (hotelId == null) return ResponseEntity.badRequest().body("hotelId_required");

        MaintenanceRequestDto created = createMaintenanceRequest(dto, hotelId, dto.getReportedByGuestId());
        return ResponseEntity.ok(created);
    }

    public MaintenanceRequestDto createMaintenanceRequest(MaintenanceRequestCreateDto dto, UUID hotelId, UUID reportedByGuestId) {
        // Direct persistence is owned by repository.
        var created = maintenanceRequestAssignmentWorkflowRepository.createMaintenanceRequest(dto, hotelId, reportedByGuestId);
        return created;
    }

    public ResponseEntity<?> assignTechnician(UUID requestId, UUID technicianId, Principal principal) {
        if (requestId == null || technicianId == null) return ResponseEntity.badRequest().body("requestId_and_technicianId_required");
        MaintenanceRequestDto updated = assignTechnician(requestId, technicianId);
        return ResponseEntity.ok(updated);
    }

    public MaintenanceRequestDto assignTechnician(UUID requestId, UUID technicianId) {
        return maintenanceRequestAssignmentWorkflowRepository.assignTechnician(requestId, technicianId);
    }

    public ResponseEntity<?> reassignTechnician(UUID requestId, UUID technicianId, Principal principal) {
        if (requestId == null || technicianId == null) return ResponseEntity.badRequest().body("requestId_and_technicianId_required");
        MaintenanceRequestDto updated = reassignTechnician(requestId, technicianId);
        return ResponseEntity.ok(updated);
    }

    public MaintenanceRequestDto reassignTechnician(UUID requestId, UUID technicianId) {
        return maintenanceRequestAssignmentWorkflowRepository.reassignTechnician(requestId, technicianId);
    }

    public ResponseEntity<?> startMaintenance(UUID requestId, Principal principal) {
        if (requestId == null) return ResponseEntity.badRequest().body("requestId_required");
        MaintenanceRequestDto updated = startMaintenance(requestId);
        return ResponseEntity.ok(updated);
    }

    public MaintenanceRequestDto startMaintenance(UUID requestId) {
        return maintenanceRequestAssignmentWorkflowRepository.startMaintenance(requestId);
    }

    public ResponseEntity<?> resolveMaintenance(UUID requestId, BigDecimal estimatedCost, Instant resolvedAt, Principal principal) {
        if (requestId == null) return ResponseEntity.badRequest().body("requestId_required");
        if (resolvedAt == null) return ResponseEntity.badRequest().body("resolvedAt_required");
        MaintenanceRequestDto updated = resolveMaintenance(requestId, estimatedCost, resolvedAt);
        return ResponseEntity.ok(updated);
    }

    public MaintenanceRequestDto resolveMaintenance(UUID requestId, BigDecimal estimatedCost, Instant resolvedAt) {
        return maintenanceRequestAssignmentWorkflowRepository.resolveMaintenance(requestId, estimatedCost, resolvedAt);
    }

    public ResponseEntity<?> closeMaintenance(UUID requestId, Principal principal) {
        if (requestId == null) return ResponseEntity.badRequest().body("requestId_required");
        MaintenanceRequestDto updated = closeMaintenance(requestId);
        return ResponseEntity.ok(updated);
    }

    public MaintenanceRequestDto closeMaintenance(UUID requestId) {
        return maintenanceRequestAssignmentWorkflowRepository.closeMaintenance(requestId);
    }

    public ResponseEntity<Page<MaintenanceRequestDto>> searchMaintenanceRequests(String search, UUID hotelId, Pageable pageable, Principal principal) {
        if (hotelId == null) return ResponseEntity.badRequest().build();
        Page<MaintenanceRequestDto> page = searchMaintenanceRequests(search, hotelId, pageable);
        return ResponseEntity.ok(page);
    }

    public Page<MaintenanceRequestDto> searchMaintenanceRequests(String search, UUID hotelId, Pageable pageable) {
        return maintenanceRequestAssignmentWorkflowRepository.searchMaintenanceRequests(search, hotelId, pageable);
    }
}
