package com.synth.hotelhousekeepingservice.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code MANAGER} role processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class ManagerStaffStrategy implements StaffProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(ManagerStaffStrategy.class);

    @Override
    public boolean supports(String role) {
        return "MANAGER".equals(role);
    }

    @Override
    public void execute(Staff entity) {
        log.debug("Executing MANAGER strategy for {} id={}", "Staff", entity.getId());
        // TODO: add MANAGER-specific processing here
    }
}
