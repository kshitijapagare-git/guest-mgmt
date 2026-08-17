package com.synth.hotelhousekeepingservice.housekeepingTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code LINEN_CHANGE} taskType processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class LinenChangeHousekeepingTaskStrategy implements HousekeepingTaskProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(LinenChangeHousekeepingTaskStrategy.class);

    @Override
    public boolean supports(String taskType) {
        return "LINEN_CHANGE".equals(taskType);
    }

    @Override
    public void execute(HousekeepingTask entity) {
        log.debug("Executing LINEN_CHANGE strategy for {} id={}", "HousekeepingTask", entity.getId());
        // TODO: add LINEN_CHANGE-specific processing here
    }
}
