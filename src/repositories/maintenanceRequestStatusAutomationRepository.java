package <no value>.maintenance.request.status.automation;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.util.UUID;

import <no value>.maintenance.request.status.automation.model.MaintenanceRequest;

@Repository
public class MaintenanceRequestStatusAutomationRepository {

    private final MaintenanceRequestJpaRepository maintenanceRequestJpaRepository;

    public MaintenanceRequestStatusAutomationRepository(MaintenanceRequestJpaRepository maintenanceRequestJpaRepository) {
        this.maintenanceRequestJpaRepository = maintenanceRequestJpaRepository;
    }

    public List<MaintenanceRequest> findMaintenanceRequestsNeedingAutomation(Instant now, int limit) {
        // Stateless selection owned by repository.
        // Policy: requests not CLOSED where SLA window based on created/updated timestamps indicates automation.
        return maintenanceRequestJpaRepository.findNeedingAutomation(now, limit);
    }

    @Transactional
    public MaintenanceRequest saveMaintenanceRequest(MaintenanceRequest request) {
        return maintenanceRequestJpaRepository.save(request);
    }

    public Optional<MaintenanceRequest> findById(UUID id) {
        return maintenanceRequestJpaRepository.findById(id);
    }

    // Internal Jpa repository
    public interface MaintenanceRequestJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<MaintenanceRequest, UUID> {
        @org.springframework.data.jpa.repository.Query(value = """
            select r.* from maintenance_requests r
            where r.status <> 'CLOSED'
              and r.scheduled_date <= :now
            order by r.status asc
            limit :limit
        """, nativeQuery = true)
        List<MaintenanceRequest> findNeedingAutomation(Instant now, int limit);
    }
}
