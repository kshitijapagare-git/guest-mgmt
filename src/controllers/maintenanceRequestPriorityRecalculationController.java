package <no value>.maintenance.request.priority.recalculation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MaintenanceRequestPriorityRecalculationController {

    private final MaintenanceRequestPriorityRecalculationService maintenanceRequestPriorityRecalculationService;

    public MaintenanceRequestPriorityRecalculationController(MaintenanceRequestPriorityRecalculationService maintenanceRequestPriorityRecalculationService) {
        this.maintenanceRequestPriorityRecalculationService = maintenanceRequestPriorityRecalculationService;
    }

    @PostMapping("/hotels/{hotelId}/maintenance-requests/recalculate-priorities")
    public ResponseEntity<Void> recalculate(
            @PathVariable UUID hotelId
    ) {
        maintenanceRequestPriorityRecalculationService.recalculateMaintenanceRequestPrioritiesForHotel(hotelId);
        return ResponseEntity.ok().build();
    }
}
