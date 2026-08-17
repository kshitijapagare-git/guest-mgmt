package <no value>.maintenance.request.status.automation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import <no value>.maintenance.request.status.automation.MaintenanceRequestStatusAutomationService;

@RestController
@RequestMapping("/api")
public class MaintenanceRequestStatusAutomationController {

    private final MaintenanceRequestStatusAutomationService maintenanceRequestStatusAutomationService;

    public MaintenanceRequestStatusAutomationController(MaintenanceRequestStatusAutomationService maintenanceRequestStatusAutomationService) {
        this.maintenanceRequestStatusAutomationService = maintenanceRequestStatusAutomationService;
    }

    @PostMapping("/maintenance-requests/status-automation/trigger")
    public ResponseEntity<Void> maintenanceRequestStatusAutomationTrigger() {
        maintenanceRequestStatusAutomationService.runMaintenanceRequestStatusAutomation();
        return ResponseEntity.ok().build();
    }
}
