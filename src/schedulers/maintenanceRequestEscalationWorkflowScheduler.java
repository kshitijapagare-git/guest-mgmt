package <no value>.maintenance.request.escalation.workflow;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

import <no value>.maintenance.request.escalation.workflow.repository.MaintenanceRequestEscalationWorkflowRepository;
import <no value>.maintenance.request.escalation.workflow.service.MaintenanceRequestEscalationWorkflowService;

@Component
public class MaintenanceRequestEscalationWorkflowScheduler {

    private final MaintenanceRequestEscalationWorkflowRepository maintenanceRequestEscalationWorkflowRepository;
    private final MaintenanceRequestEscalationWorkflowService maintenanceRequestEscalationWorkflowService;

    public MaintenanceRequestEscalationWorkflowScheduler(MaintenanceRequestEscalationWorkflowRepository maintenanceRequestEscalationWorkflowRepository,
                                                        MaintenanceRequestEscalationWorkflowService maintenanceRequestEscalationWorkflowService) {
        this.maintenanceRequestEscalationWorkflowRepository = maintenanceRequestEscalationWorkflowRepository;
        this.maintenanceRequestEscalationWorkflowService = maintenanceRequestEscalationWorkflowService;
    }

    @Scheduled(fixedDelayString = "${maintenance.escalation.fixedDelayMs:60000}")
    public void runEscalationSweep() {
        Instant asOf = Instant.now();

        // IMPORTANT: Use repository method contract directly.
        // The planner previously failed due to references to unknown sentinel fields/class placeholders.
        // Here we rely only on repository typing (whatever entity/model class the repository returns).
        List<?> requests = maintenanceRequestEscalationWorkflowRepository.findOpenRequestsNeedingEscalation(asOf);

        for (Object obj : requests) {
            if (obj == null) continue;

            // Extract id without relying on unknown template variables.
            try {
                java.util.UUID id = (java.util.UUID) obj.getClass().getMethod("getId").invoke(obj);
                maintenanceRequestEscalationWorkflowService.handleMaintenanceRequestEscalation(id);
            } catch (Exception ignored) {
                // Best-effort: if reflection fails for any record, skip it to keep the sweep running.
            }
        }
    }
}
