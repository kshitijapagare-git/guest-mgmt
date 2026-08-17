package com.synth.hotelhousekeepingservice.housekeepingTask;

import org.springframework.lang.Nullable;
import java.util.UUID;

public record HousekeepingTaskFilterRequest(
        @Nullable UUID hotelId,
        @Nullable UUID roomId,
        @Nullable UUID assignedStaffId,
        @Nullable String status,
        @Nullable String taskType
) {
    public HousekeepingTaskFilterRequest() {
        this(null, null, null, null, null);
    }
}
