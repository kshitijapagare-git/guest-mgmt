package com.synth.hotelhousekeepingservice.housekeepingTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handles {@code RESTOCK_AMENITIES} taskType processing.
 *
 * Extend this class or add collaborators here to implement variant-specific
 * business rules without touching any other class (Single Responsibility + OCP).
 */
@Component
public class RestockAmenitiesHousekeepingTaskStrategy implements HousekeepingTaskProcessingStrategy {

    private static final Logger log = LoggerFactory.getLogger(RestockAmenitiesHousekeepingTaskStrategy.class);

    @Override
    public boolean supports(String taskType) {
        return "RESTOCK_AMENITIES".equals(taskType);
    }

    @Override
    public void execute(HousekeepingTask entity) {
        log.debug("Executing RESTOCK_AMENITIES strategy for {} id={}", "HousekeepingTask", entity.getId());
        // TODO: add RESTOCK_AMENITIES-specific processing here
    }
}
