package com.synth.hotelhousekeepingservice.housekeepingTask;

/**
 * Strategy for HousekeepingTask processing, dispatched by the {@code taskType} field.
 *
 * Implement this interface and annotate with {@code @Component} to support a new
 * variant — no changes required in service or factory code (Open/Closed Principle).
 */
public interface HousekeepingTaskProcessingStrategy {

    /** Returns true when this strategy handles the given taskType value. */
    boolean supports(String taskType);

    /** Apply strategy-specific logic to the entity before it is persisted. */
    void execute(HousekeepingTask entity);
}
