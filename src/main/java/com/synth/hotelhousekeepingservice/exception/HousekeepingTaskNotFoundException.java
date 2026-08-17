package com.synth.hotelhousekeepingservice.exception;
import java.util.UUID;

public class HousekeepingTaskNotFoundException extends ResourceNotFoundException {

    public HousekeepingTaskNotFoundException(UUID id) {
        super("HousekeepingTask not found: " + id);
    }
}
