package com.synth.hotelhousekeepingservice.maintenanceRequest;
import java.util.UUID;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import com.synth.hotelhousekeepingservice.staff.Staff;

public record MaintenanceRequestResponse(
        UUID id,
        UUID hotelId,
        UUID roomId,
        UUID reportedByGuestId,
        String issueType,
        String description,
        String priority,
        String status,
        BigDecimal estimatedCost,
        LocalDateTime resolvedAt,
        StaffSummary assignedTechnicianId
) {
    public record StaffSummary(UUID id, String firstName, String lastName, String email, String phone, String role, String status) {}

    public static MaintenanceRequestResponse from(MaintenanceRequest entity) {
        return new MaintenanceRequestResponse(
entity.getId(),
entity.getHotelId(),
entity.getRoomId(),
entity.getReportedByGuestId(),
entity.getIssueType(),
entity.getDescription(),
entity.getPriority(),
entity.getStatus(),
entity.getEstimatedCost(),
entity.getResolvedAt(),
entity.getAssignedTechnicianId() != null
                        ? new StaffSummary(entity.getAssignedTechnicianId().getId(), entity.getAssignedTechnicianId().getFirstName(), entity.getAssignedTechnicianId().getLastName(), entity.getAssignedTechnicianId().getEmail(), entity.getAssignedTechnicianId().getPhone(), entity.getAssignedTechnicianId().getRole(), entity.getAssignedTechnicianId().getStatus())
                        : null
        );
    }
}
