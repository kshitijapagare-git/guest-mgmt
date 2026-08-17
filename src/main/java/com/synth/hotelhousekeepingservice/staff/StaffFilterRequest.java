package com.synth.hotelhousekeepingservice.staff;

import org.springframework.lang.Nullable;

public record StaffFilterRequest(
        @Nullable String role,
        @Nullable String status,
        @Nullable String search
) {
    public StaffFilterRequest() {
        this(null, null, null);
    }
}
