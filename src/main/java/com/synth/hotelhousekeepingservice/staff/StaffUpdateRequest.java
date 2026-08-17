package com.synth.hotelhousekeepingservice.staff;
import jakarta.validation.constraints.*;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Payload accepted by PUT /staffs/{id}.
 *
 * Distinct from StaffCreateRequest so the MapStruct mapper can
 * apply NullValuePropertyMappingStrategy.IGNORE — null fields in the
 * incoming payload leave the entity's existing value untouched.
 */
public record StaffUpdateRequest(
        @NotBlank @Size(max = 80) String firstName,
        @NotBlank @Size(max = 80) String lastName,
        @NotBlank @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$") @Size(max = 255) String email,
        @NotBlank @Size(max = 20) String phone,
        @Pattern(regexp = "^(HOUSEKEEPER|SUPERVISOR|MAINTENANCE_TECH|MANAGER)$") @NotBlank String role,
        @Pattern(regexp = "^(ACTIVE|ON_LEAVE|INACTIVE)$") @NotBlank String status
) {}
