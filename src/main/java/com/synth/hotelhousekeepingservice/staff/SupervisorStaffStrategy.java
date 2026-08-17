package com.synth.hotelhousekeepingservice.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code SUPERVISOR} role processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class SupervisorStaffStrategy implements StaffProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(SupervisorStaffStrategy.class);

    @Override
    public boolean supports(String role) {
        return "SUPERVISOR".equals(role);
    }

    @Override
    public void execute(Staff entity) {
        log.debug("Executing SUPERVISOR strategy for {} id={}", "Staff", entity.getId());
        // TODO: add SUPERVISOR-specific processing here
    }
}
