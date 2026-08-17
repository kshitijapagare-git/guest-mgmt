package com.synth.hotelhousekeepingservice.maintenanceRequest;

import org.springframework.lang.Nullable;
import java.util.UUID;

public record MaintenanceRequestFilterRequest(
        @Nullable UUID hotelId,
        @Nullable UUID roomId,
        @Nullable UUID assignedTechnicianId,
        @Nullable String status,
        @Nullable String priority,
        @Nullable String search
) {
    public MaintenanceRequestFilterRequest() {
        this(null, null, null, null, null, null);
    }
}
