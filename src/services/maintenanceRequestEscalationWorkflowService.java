package <no value>.maintenance.request.escalation.workflow;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import <no value>.maintenance.request.escalation.workflow.model.MaintenanceRequest;

@Service
public class MaintenanceRequestEscalationWorkflowService {

    private final MaintenanceRequestEscalationWorkflowRepository maintenanceRequestEscalationWorkflowRepository;

    public MaintenanceRequestEscalationWorkflowService(MaintenanceRequestEscalationWorkflowRepository maintenanceRequestEscalationWorkflowRepository) {
        this.maintenanceRequestEscalationWorkflowRepository = maintenanceRequestEscalationWorkflowRepository;
    }

    public void handleMaintenanceRequestEscalation(UUID maintenanceRequestId) {
        if (maintenanceRequestId == null) throw new IllegalArgumentException("maintenanceRequestId_required");

        MaintenanceRequest request = maintenanceRequestEscalationWorkflowRepository.findById(maintenanceRequestId)
                .orElseThrow(() -> new java.util.NoSuchElementException("maintenanceRequest_not_found"));

        // Escalation rule: if request is not CLOSED, bump priority and move status one step forward when applicable.
        if (request.getStatus() == MaintenanceRequest.Status.CLOSED) {
            return;
        }

        // Priority escalation: LOW->NORMAL->HIGH->URGENT
        MaintenanceRequest.Priority p = request.getPriority();
        request.setPriority(switch (p) {
            case LOW -> MaintenanceRequest.Priority.NORMAL;
            case NORMAL -> MaintenanceRequest.Priority.HIGH;
            case HIGH -> MaintenanceRequest.Priority.URGENT;
            case URGENT -> MaintenanceRequest.Priority.URGENT;
        });

        // Status step-forward: OPEN/ASSIGNED/IN_PROGRESS/RESOLVED
        request.setStatus(switch (request.getStatus()) {
            case OPEN -> MaintenanceRequest.Status.ASSIGNED;
            case ASSIGNED -> MaintenanceRequest.Status.IN_PROGRESS;
            case IN_PROGRESS -> MaintenanceRequest.Status.RESOLVED;
            case RESOLVED -> MaintenanceRequest.Status.CLOSED;
            case CLOSED -> MaintenanceRequest.Status.CLOSED;
        });

        if (request.getStatus() == MaintenanceRequest.Status.RESOLVED || request.getStatus() == MaintenanceRequest.Status.CLOSED) {
            if (request.getResolvedAt() == null) request.setResolvedAt(Instant.now());
        }

        maintenanceRequestEscalationWorkflowRepository.save(request);
    }
}
