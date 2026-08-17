package com.synth.hotelhousekeepingservice.housekeepingTask;
import java.util.UUID;
import java.time.Instant;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import com.synth.hotelhousekeepingservice.staff.Staff;

public record HousekeepingTaskResponse(
        UUID id,
        UUID hotelId,
        UUID roomId,
        String taskType,
        String priority,
        String status,
        LocalDate scheduledDate,
        Instant completedAt,
        String notes,
        StaffSummary assignedStaffId
) {
    public record StaffSummary(UUID id, String firstName, String lastName, String email, String phone, String role, String status) {}

    public static HousekeepingTaskResponse from(HousekeepingTask entity) {
        return new HousekeepingTaskResponse(
entity.getId(),
entity.getHotelId(),
entity.getRoomId(),
entity.getTaskType(),
entity.getPriority(),
entity.getStatus(),
entity.getScheduledDate(),
entity.getCompletedAt(),
entity.getNotes(),
entity.getAssignedStaffId() != null
                        ? new StaffSummary(entity.getAssignedStaffId().getId(), entity.getAssignedStaffId().getFirstName(), entity.getAssignedStaffId().getLastName(), entity.getAssignedStaffId().getEmail(), entity.getAssignedStaffId().getPhone(), entity.getAssignedStaffId().getRole(), entity.getAssignedStaffId().getStatus())
                        : null
        );
    }
}
