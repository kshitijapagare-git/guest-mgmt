package <no value>.maintenance.request.priority.recalculation;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import <no value>.maintenance.request.priority.recalculation.model.MaintenanceRequest;

@Repository
public class MaintenanceRequestPriorityRecalculationRepository {

    private final MaintenanceRequestJpaRepository maintenanceRequestJpaRepository;

    public MaintenanceRequestPriorityRecalculationRepository(MaintenanceRequestJpaRepository maintenanceRequestJpaRepository) {
        this.maintenanceRequestJpaRepository = maintenanceRequestJpaRepository;
    }

    public List<MaintenanceRequest> findAllMaintenanceRequestsForHotelOrdered(UUID hotelId) {
        if (hotelId == null) throw new IllegalArgumentException("hotelId_required");
        return maintenanceRequestJpaRepository.findByHotelIdOrderByScheduledDateAsc(hotelId);
    }

    @Transactional
    public <S extends MaintenanceRequest> List<S> saveAllMaintenanceRequests(List<S> requests) {
        if (requests == null) throw new IllegalArgumentException("requests_required");
        return maintenanceRequestJpaRepository.saveAll(requests);
    }

    public interface MaintenanceRequestJpaRepository extends org.springframework.data.jpa.repository.JpaRepository<MaintenanceRequest, UUID> {
        List<MaintenanceRequest> findByHotelIdOrderByScheduledDateAsc(UUID hotelId);
    }
}
