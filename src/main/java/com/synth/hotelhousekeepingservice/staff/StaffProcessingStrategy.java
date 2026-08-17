package com.synth.hotelhousekeepingservice.staff;

/**
 * Strategy for Staff processing, dispatched by the {@code role} field.
 *
 * Implement this interface and annotate with {@code @Component} to support a new
 * variant — no changes required in service or factory code (Open/Closed Principle).
 */
public interface StaffProcessingStrategy {

    /** Returns true when this strategy handles the given role value. */
    boolean supports(String role);

    /** Apply strategy-specific logic to the entity before it is persisted. */
    void execute(Staff entity);
}
