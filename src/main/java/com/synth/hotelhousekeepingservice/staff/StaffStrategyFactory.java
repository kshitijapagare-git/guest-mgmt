package com.synth.hotelhousekeepingservice.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Resolves the correct {@link StaffProcessingStrategy} at runtime.
 *
 * Uses the Null Object pattern: when no registered strategy matches, a no-op
 * implementation is returned so the application never throws on an unknown
 * variant — behaviour can be upgraded by registering a new {@code @Component}.
 */
@Component
public class StaffStrategyFactory {

    private static final Logger log = LoggerFactory.getLogger(StaffStrategyFactory.class);

    private final List<StaffProcessingStrategy> strategies;

    public StaffStrategyFactory(List<StaffProcessingStrategy> strategies) {
        this.strategies = strategies;
    }

    public StaffProcessingStrategy resolve(String role) {
        return strategies.stream()
                .filter(s -> s.supports(role))
                .findFirst()
                .orElseGet(() -> new NoOpStrategy(role));
    }

    /** Null Object — logs a warning and does nothing when no strategy is registered. */
    private static final class NoOpStrategy implements StaffProcessingStrategy {
        private final String variant;
        NoOpStrategy(String variant) { this.variant = variant; }

        @Override
        public boolean supports(String value) { return true; }

        @Override
        public void execute(Staff entity) {
            log.warn("No StaffProcessingStrategy registered for role='{}'; applying no-op (Null Object).", variant);
        }
    }
}
