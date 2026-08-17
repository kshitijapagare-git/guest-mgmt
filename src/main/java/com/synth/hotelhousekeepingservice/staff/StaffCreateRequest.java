package com.synth.hotelhousekeepingservice.staff;
import jakarta.validation.constraints.*;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by POST /staffs.
 *
 * Emitted as a separate type from StaffUpdateRequest so MapStruct
 * can map them with different policies — create-time we want to populate
 * server-managed fields; update-time we want to ignore null values.
 */
public record StaffCreateRequest(
        @NotBlank @Size(max = 80) String firstName,
        @NotBlank @Size(max = 80) String lastName,
        @NotBlank @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$") @Size(max = 255) String email,
        @NotBlank @Size(max = 20) String phone,
        @Pattern(regexp = "^(HOUSEKEEPER|SUPERVISOR|MAINTENANCE_TECH|MANAGER)$") @NotBlank String role,
        @Pattern(regexp = "^(ACTIVE|ON_LEAVE|INACTIVE)$") @NotBlank String status
) {
}
