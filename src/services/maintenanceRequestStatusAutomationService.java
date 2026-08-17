package <no value>.maintenance.request.status.automation;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import <no value>.maintenance.request.status.automation.model.MaintenanceRequest;

@Service
public class MaintenanceRequestStatusAutomationService {

    private final MaintenanceRequestStatusAutomationRepository maintenanceRequestStatusAutomationRepository;

    public MaintenanceRequestStatusAutomationService(MaintenanceRequestStatusAutomationRepository maintenanceRequestStatusAutomationRepository) {
        this.maintenanceRequestStatusAutomationRepository = maintenanceRequestStatusAutomationRepository;
    }

    public void runMaintenanceRequestStatusAutomation() {
        // Config-driven automation policy (thresholds) should come from external config.
        // For now, logic is: for each eligible request, if time-based rule matches, transition.
        Instant now = Instant.now();
        int limit = 500; // could be externalized by later generator pass if config exists

        List<MaintenanceRequest> candidates = maintenanceRequestStatusAutomationRepository.findMaintenanceRequestsNeedingAutomation(now, limit);
        for (MaintenanceRequest req : candidates) {
            if (req == null) continue;
            Instant resolvedAt = req.getResolvedAt();

            // Example automation: if status OPEN and has an estimatedCost threshold elapsed without resolution,
            // move forward. Since only repository exposes 'needing automation', repository already filtered.
            // Here we implement a deterministic, monotonic transition based on current status.
            switch (req.getStatus()) {
                case OPEN -> req.setStatus(MaintenanceRequest.Status.ASSIGNED);
                case ASSIGNED -> req.setStatus(MaintenanceRequest.Status.IN_PROGRESS);
                case IN_PROGRESS -> req.setStatus(MaintenanceRequest.Status.RESOLVED);
                case RESOLVED -> req.setStatus(MaintenanceRequest.Status.CLOSED);
                default -> {}
            }

            // If moving to RESOLVED or beyond, ensure resolvedAt is set.
            if (req.getStatus() == MaintenanceRequest.Status.RESOLVED || req.getStatus() == MaintenanceRequest.Status.CLOSED) {
                if (resolvedAt == null) req.setResolvedAt(now);
            }

            maintenanceRequestStatusAutomationRepository.saveMaintenanceRequest(req);
        }
    }
}
