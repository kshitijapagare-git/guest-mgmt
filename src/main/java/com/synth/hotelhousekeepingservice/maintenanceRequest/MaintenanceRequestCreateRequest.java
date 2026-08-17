package com.synth.hotelhousekeepingservice.maintenanceRequest;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by POST /maintenance-requests.
 *
 * Emitted as a separate type from MaintenanceRequestUpdateRequest so MapStruct
 * can map them with different policies — create-time we want to populate
 * server-managed fields; update-time we want to ignore null values.
 */
public record MaintenanceRequestCreateRequest(
        @NotNull UUID hotelId,
        @NotNull UUID roomId,
        UUID reportedByGuestId,
        @Pattern(regexp = "^(PLUMBING|ELECTRICAL|HVAC|FURNITURE|APPLIANCE|OTHER)$") @NotBlank String issueType,
        @NotBlank @Size(max = 1000) String description,
        @Pattern(regexp = "^(LOW|NORMAL|HIGH|URGENT)$") @NotBlank String priority,
        @Pattern(regexp = "^(OPEN|ASSIGNED|IN_PROGRESS|RESOLVED|CLOSED)$") @NotBlank String status,
        @Min(0) BigDecimal estimatedCost,
        Instant resolvedAt,
        UUID assignedTechnicianId
) {
}
