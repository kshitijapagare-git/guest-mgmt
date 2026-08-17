package com.synth.hotelhousekeepingservice.exception;
import java.util.UUID;

public class StaffNotFoundException extends ResourceNotFoundException {

    public StaffNotFoundException(UUID id) {
        super("Staff not found: " + id);
    }
}
