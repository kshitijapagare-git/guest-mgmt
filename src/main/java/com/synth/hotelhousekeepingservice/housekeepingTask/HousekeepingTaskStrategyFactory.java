package com.synth.hotelhousekeepingservice.housekeepingTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Resolves the correct {@link HousekeepingTaskProcessingStrategy} at runtime.
 *
 * Uses the Null Object pattern: when no registered strategy matches, a no-op
 * implementation is returned so the application never throws on an unknown
 * variant — behaviour can be upgraded by registering a new {@code @Component}.
 */
@Component
public class HousekeepingTaskStrategyFactory {

    private static final Logger log = LoggerFactory.getLogger(HousekeepingTaskStrategyFactory.class);

    private final List<HousekeepingTaskProcessingStrategy> strategies;

    public HousekeepingTaskStrategyFactory(List<HousekeepingTaskProcessingStrategy> strategies) {
        this.strategies = strategies;
    }

    public HousekeepingTaskProcessingStrategy resolve(String taskType) {
        return strategies.stream()
                .filter(s -> s.supports(taskType))
                .findFirst()
                .orElseGet(() -> new NoOpStrategy(taskType));
    }

    /** Null Object — logs a warning and does nothing when no strategy is registered. */
    private static final class NoOpStrategy implements HousekeepingTaskProcessingStrategy {
        private final String variant;
        NoOpStrategy(String variant) { this.variant = variant; }

        @Override
        public boolean supports(String value) { return true; }

        @Override
        public void execute(HousekeepingTask entity) {
            log.warn("No HousekeepingTaskProcessingStrategy registered for taskType='{}'; applying no-op (Null Object).", variant);
        }
    }
}
