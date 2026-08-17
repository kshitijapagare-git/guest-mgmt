package com.synth.hotelhousekeepingservice.staff;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;

public record StaffResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String role,
        String status
) {

    public static StaffResponse from(Staff entity) {
        return new StaffResponse(
entity.getId(),
entity.getFirstName(),
entity.getLastName(),
entity.getEmail(),
entity.getPhone(),
entity.getRole(),
entity.getStatus()
        );
    }
}
