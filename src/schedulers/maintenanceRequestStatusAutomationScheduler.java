package <no value>.maintenance.request.status.automation;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceRequestStatusAutomationScheduler {

    private final MaintenanceRequestStatusAutomationService maintenanceRequestStatusAutomationService;

    public MaintenanceRequestStatusAutomationScheduler(MaintenanceRequestStatusAutomationService maintenanceRequestStatusAutomationService) {
        this.maintenanceRequestStatusAutomationService = maintenanceRequestStatusAutomationService;
    }

    @Scheduled(fixedDelayString = "${maintenance.statusAutomation.fixedDelayMs:60000}")
    public void scheduleMaintenanceRequestStatusAutomation() {
        maintenanceRequestStatusAutomationService.runMaintenanceRequestStatusAutomation();
    }
}
