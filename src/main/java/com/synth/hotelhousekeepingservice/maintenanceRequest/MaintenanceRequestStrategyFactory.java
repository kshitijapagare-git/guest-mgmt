package com.synth.hotelhousekeepingservice.maintenanceRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Resolves the correct {@link MaintenanceRequestProcessingStrategy} at runtime.
 *
 * Uses the Null Object pattern: when no registered strategy matches, a no-op
 * implementation is returned so the application never throws on an unknown
 * variant — behaviour can be upgraded by registering a new {@code @Component}.
 */
@Component
public class MaintenanceRequestStrategyFactory {

    private static final Logger log = LoggerFactory.getLogger(MaintenanceRequestStrategyFactory.class);

    private final List<MaintenanceRequestProcessingStrategy> strategies;

    public MaintenanceRequestStrategyFactory(List<MaintenanceRequestProcessingStrategy> strategies) {
        this.strategies = strategies;
    }

    public MaintenanceRequestProcessingStrategy resolve(String issueType) {
        return strategies.stream()
                .filter(s -> s.supports(issueType))
                .findFirst()
                .orElseGet(() -> new NoOpStrategy(issueType));
    }

    /** Null Object — logs a warning and does nothing when no strategy is registered. */
    private static final class NoOpStrategy implements MaintenanceRequestProcessingStrategy {
        private final String variant;
        NoOpStrategy(String variant) { this.variant = variant; }

        @Override
        public boolean supports(String value) { return true; }

        @Override
        public void execute(MaintenanceRequest entity) {
            log.warn("No MaintenanceRequestProcessingStrategy registered for issueType='{}'; applying no-op (Null Object).", variant);
        }
    }
}
