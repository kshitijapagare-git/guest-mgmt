package com.synth.hotelhousekeepingservice.maintenanceRequest;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Objects;
import com.synth.hotelhousekeepingservice.staff.Staff;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "maintenance_requests")
@EntityListeners(AuditingEntityListener.class)
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID hotelId;

    @Column(nullable = false)
    private UUID roomId;

    @Column(nullable = true)
    private UUID reportedByGuestId;

    @Column(nullable = false)
    private String issueType;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String status;

    @Column(nullable = true)
    private BigDecimal estimatedCost;

    @Column(nullable = true)
    private LocalDateTime resolvedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_technician_id", nullable = true)
    private Staff assignedTechnicianId;


    // ─── Tell-Don't-Ask state predicates ──────────────────────────────────────

    public boolean isPlumbing() {
        return "PLUMBING".equals(issueType);
    }

    public boolean isElectrical() {
        return "ELECTRICAL".equals(issueType);
    }

    public boolean isHvac() {
        return "HVAC".equals(issueType);
    }

    public boolean isFurniture() {
        return "FURNITURE".equals(issueType);
    }

    public boolean isAppliance() {
        return "APPLIANCE".equals(issueType);
    }

    public boolean isOther() {
        return "OTHER".equals(issueType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaintenanceRequest other)) return false;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
