package com.synth.hotelhousekeepingservice.maintenanceRequest;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.Instant;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by PUT /maintenance-requests/{id}.
 *
 * Distinct from MaintenanceRequestCreateRequest so the MapStruct mapper can
 * apply NullValuePropertyMappingStrategy.IGNORE — null fields in the
 * incoming payload leave the entity's existing value untouched.
 */
public record MaintenanceRequestUpdateRequest(
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
) {}
