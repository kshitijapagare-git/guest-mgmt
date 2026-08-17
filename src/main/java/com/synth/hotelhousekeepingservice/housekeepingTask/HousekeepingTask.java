package com.synth.hotelhousekeepingservice.housekeepingTask;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Objects;
import com.synth.hotelhousekeepingservice.staff.Staff;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "housekeeping_tasks")
@EntityListeners(AuditingEntityListener.class)
public class HousekeepingTask {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID hotelId;

    @Column(nullable = false)
    private UUID roomId;

    @Column(nullable = false)
    private String taskType;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDate scheduledDate;

    @Column(nullable = true)
    private Instant completedAt;

    @Column(nullable = true, length = 500)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_staff_id", nullable = true)
    private Staff assignedStaffId;


    // ─── Tell-Don't-Ask state predicates ──────────────────────────────────────

    public boolean isCleaning() {
        return "CLEANING".equals(taskType);
    }

    public boolean isDeepCleaning() {
        return "DEEP_CLEANING".equals(taskType);
    }

    public boolean isLinenChange() {
        return "LINEN_CHANGE".equals(taskType);
    }

    public boolean isRestockAmenities() {
        return "RESTOCK_AMENITIES".equals(taskType);
    }

    public boolean isInspection() {
        return "INSPECTION".equals(taskType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HousekeepingTask other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
