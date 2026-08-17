package com.synth.hotelhousekeepingservice.maintenanceRequest;

/**
 * Strategy for MaintenanceRequest processing, dispatched by the {@code issueType} field.
 *
 * Implement this interface and annotate with {@code @Component} to support a new
 * variant — no changes required in service or factory code (Open/Closed Principle).
 */
public interface MaintenanceRequestProcessingStrategy {

    /** Returns true when this strategy handles the given issueType value. */
    boolean supports(String issueType);

    /** Apply strategy-specific logic to the entity before it is persisted. */
    void execute(MaintenanceRequest entity);
}
