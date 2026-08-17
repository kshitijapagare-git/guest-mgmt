package com.synth.hotelhousekeepingservice.housekeepingTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code DEEP_CLEANING} taskType processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class DeepCleaningHousekeepingTaskStrategy implements HousekeepingTaskProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(DeepCleaningHousekeepingTaskStrategy.class);

    @Override
    public boolean supports(String taskType) {
        return "DEEP_CLEANING".equals(taskType);
    }

    @Override
    public void execute(HousekeepingTask entity) {
        log.debug("Executing DEEP_CLEANING strategy for {} id={}", "HousekeepingTask", entity.getId());
        // TODO: add DEEP_CLEANING-specific processing here
    }
}
