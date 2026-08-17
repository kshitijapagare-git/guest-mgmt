package com.synth.hotelhousekeepingservice.housekeepingTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code INSPECTION} taskType processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class InspectionHousekeepingTaskStrategy implements HousekeepingTaskProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(InspectionHousekeepingTaskStrategy.class);

    @Override
    public boolean supports(String taskType) {
        return "INSPECTION".equals(taskType);
    }

    @Override
    public void execute(HousekeepingTask entity) {
        log.debug("Executing INSPECTION strategy for {} id={}", "HousekeepingTask", entity.getId());
        // TODO: add INSPECTION-specific processing here
    }
}
