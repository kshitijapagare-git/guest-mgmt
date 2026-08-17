package com.synth.hotelhousekeepingservice.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code HOUSEKEEPER} role processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class HousekeeperStaffStrategy implements StaffProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(HousekeeperStaffStrategy.class);

    @Override
    public boolean supports(String role) {
        return "HOUSEKEEPER".equals(role);
    }

    @Override
    public void execute(Staff entity) {
        log.debug("Executing HOUSEKEEPER strategy for {} id={}", "Staff", entity.getId());
        // TODO: add HOUSEKEEPER-specific processing here
    }
}
