package <no value>.maintenance.request.escalation.workflow;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MaintenanceRequestEscalationWorkflowController {

    private final MaintenanceRequestEscalationWorkflowService maintenanceRequestEscalationWorkflowService;

    public MaintenanceRequestEscalationWorkflowController(MaintenanceRequestEscalationWorkflowService maintenanceRequestEscalationWorkflowService) {
        this.maintenanceRequestEscalationWorkflowService = maintenanceRequestEscalationWorkflowService;
    }

    @PostMapping("/maintenance-requests/{requestId}/escalate")
    public ResponseEntity<?> escalateMaintenanceRequest(@PathVariable UUID requestId) {
        if (requestId == null) return ResponseEntity.badRequest().body("requestId_required");
        maintenanceRequestEscalationWorkflowService.handleMaintenanceRequestEscalation(requestId);
        return ResponseEntity.ok().build();
    }
}
