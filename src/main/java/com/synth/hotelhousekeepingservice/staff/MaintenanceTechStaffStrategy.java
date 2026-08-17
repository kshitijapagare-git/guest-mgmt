package com.synth.hotelhousekeepingservice.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code MAINTENANCE_TECH} role processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class MaintenanceTechStaffStrategy implements StaffProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(MaintenanceTechStaffStrategy.class);

    @Override
    public boolean supports(String role) {
        return "MAINTENANCE_TECH".equals(role);
    }

    @Override
    public void execute(Staff entity) {
        log.debug("Executing MAINTENANCE_TECH strategy for {} id={}", "Staff", entity.getId());
        // TODO: add MAINTENANCE_TECH-specific processing here
    }
}
