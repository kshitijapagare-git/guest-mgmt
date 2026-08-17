package com.synth.hotelhousekeepingservice.maintenanceRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code OTHER} issueType processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class OtherMaintenanceRequestStrategy implements MaintenanceRequestProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(OtherMaintenanceRequestStrategy.class);

    @Override
    public boolean supports(String issueType) {
        return "OTHER".equals(issueType);
    }

    @Override
    public void execute(MaintenanceRequest entity) {
        log.debug("Executing OTHER strategy for {} id={}", "MaintenanceRequest", entity.getId());
        // TODO: add OTHER-specific processing here
    }
}
