package <no value>.maintenance.request.escalation.workflow;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import <no value>.maintenance.request.escalation.workflow.model.MaintenanceRequest;

@Repository
public class MaintenanceRequestEscalationWorkflowRepository {

    private final MaintenanceRequestJpaRepository maintenanceRequestJpaRepository;

    public MaintenanceRequestEscalationWorkflowRepository(MaintenanceRequestJpaRepository maintenanceRequestJpaRepository) {
        this.maintenanceRequestJpaRepository = maintenanceRequestJpaRepository;
    }

    public Optional<MaintenanceRequest> findById(UUID id) {
        return maintenanceRequestJpaRepository.findById(id);
    }

    @Transactional
    public MaintenanceRequest save(MaintenanceRequest entity) {
        return maintenanceRequestJpaRepository.save(entity);
    }

    public List<MaintenanceRequest> findOpenRequestsNeedingEscalation(Instant asOf) {
        return maintenanceRequestJpaRepository.findNeedingEscalation(asOf);
    }

    public interface MaintenanceRequestJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<MaintenanceRequest, UUID> {
        @org.springframework.data.jpa.repository.Query(value = """
            select r.* from maintenance_requests r
            where r.status in ('OPEN','ASSIGNED','IN_PROGRESS')
              and r.scheduled_date <= :asOf
            order by r.priority asc
        """, nativeQuery = true)
        List<MaintenanceRequest> findNeedingEscalation(Instant asOf);
    }
}
