package com.synth.hotelhousekeepingservice.housekeepingTask;
import jakarta.validation.constraints.*;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by PUT /housekeeping-tasks/{id}.
 *
 * Distinct from HousekeepingTaskCreateRequest so the MapStruct mapper can
 * apply NullValuePropertyMappingStrategy.IGNORE — null fields in the
 * incoming payload leave the entity's existing value untouched.
 */
public record HousekeepingTaskUpdateRequest(
        @NotNull UUID hotelId,
        @NotNull UUID roomId,
        @Pattern(regexp = "^(CLEANING|DEEP_CLEANING|LINEN_CHANGE|RESTOCK_AMENITIES|INSPECTION)$") @NotBlank String taskType,
        @Pattern(regexp = "^(LOW|NORMAL|HIGH|URGENT)$") @NotBlank String priority,
        @Pattern(regexp = "^(PENDING|IN_PROGRESS|COMPLETED|SKIPPED)$") @NotBlank String status,
        @NotNull LocalDate scheduledDate,
        LocalDateTime completedAt,
        @Size(max = 500) String notes,
        UUID assignedStaffId
) {}
