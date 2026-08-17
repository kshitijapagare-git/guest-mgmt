package com.synth.hotelhousekeepingservice.exception;
import java.util.UUID;

public class MaintenanceRequestNotFoundException extends ResourceNotFoundException {

    public MaintenanceRequestNotFoundException(UUID id) {
        super("MaintenanceRequest not found: " + id);
    }
}
