package <no value>.maintenance.request.priority.recalculation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import <no value>.maintenance.request.priority.recalculation.model.MaintenanceRequest;

@Service
public class MaintenanceRequestPriorityRecalculationService {

    private final MaintenanceRequestPriorityRecalculationRepository maintenanceRequestPriorityRecalculationRepository;

    public MaintenanceRequestPriorityRecalculationService(MaintenanceRequestPriorityRecalculationRepository maintenanceRequestPriorityRecalculationRepository) {
        this.maintenanceRequestPriorityRecalculationRepository = maintenanceRequestPriorityRecalculationRepository;
    }

    public void recalculateMaintenanceRequestPrioritiesForHotel(UUID hotelId) {
        if (hotelId == null) throw new IllegalArgumentException("hotelId_required");

        List<MaintenanceRequest> requests = maintenanceRequestPriorityRecalculationRepository.findAllMaintenanceRequestsForHotelOrdered(hotelId);
        if (requests == null || requests.isEmpty()) return;

        // Deterministic priority recalculation: older requests move up; resolved/closed stay unchanged.
        // Priority becomes a function of days since scheduled date.
        Instant now = Instant.now();
        for (MaintenanceRequest r : requests) {
            if (r == null) continue;
            if (r.getStatus() == MaintenanceRequest.Status.RESOLVED || r.getStatus() == MaintenanceRequest.Status.CLOSED) {
                continue;
            }

            // Example scoring; thresholds should be config-driven, but kept as direct computation.
            long ageHours = Duration.between(r.getScheduledDate().atStartOfDay(ZoneOffset.UTC), now).toHours();

            MaintenanceRequest.Priority newPriority;
            if (ageHours >= 72) newPriority = MaintenanceRequest.Priority.URGENT;
            else if (ageHours >= 48) newPriority = MaintenanceRequest.Priority.HIGH;
            else if (ageHours >= 24) newPriority = MaintenanceRequest.Priority.NORMAL;
            else newPriority = MaintenanceRequest.Priority.LOW;

            r.setPriority(newPriority);
        }

        maintenanceRequestPriorityRecalculationRepository.saveAllMaintenanceRequests(requests);
    }
}
