package com.synth.hotelhousekeepingservice.housekeepingTask;

import com.synth.hotelhousekeepingservice.exception.HousekeepingTaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/housekeeping-tasks")
@Tag(name = "HousekeepingTask Status Transitions")
@SecurityRequirement(name = "bearerAuth")
public class HousekeepingTaskStatusTransitionController {

    private final HousekeepingTaskRepository repository;

    @PostMapping("/{id}/start")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Staff starts the housekeeping task")
    @Transactional
    public HousekeepingTaskResponse start(@PathVariable UUID id) {
        HousekeepingTask entity = repository.findById(id)
                .orElseThrow(() -> new HousekeepingTaskNotFoundException(id));
        if (!entity.getStatus().equals("PENDING")) {
            throw new IllegalStateException(
                    "Status must be 'PENDING' to perform this transition, current is '" + entity.getStatus() + "'");
        }
        entity.setStatus("IN_PROGRESS");
        return HousekeepingTaskResponse.from(repository.save(entity));
    }

    @PostMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Task finished")
    @Transactional
    public HousekeepingTaskResponse complete(@PathVariable UUID id) {
        HousekeepingTask entity = repository.findById(id)
                .orElseThrow(() -> new HousekeepingTaskNotFoundException(id));
        if (!entity.getStatus().equals("IN_PROGRESS")) {
            throw new IllegalStateException(
                    "Status must be 'IN_PROGRESS' to perform this transition, current is '" + entity.getStatus() + "'");
        }
        entity.setStatus("COMPLETED");
        return HousekeepingTaskResponse.from(repository.save(entity));
    }

    @PostMapping("/{id}/skip")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Task skipped (e.g. room occupied / do-not-disturb)")
    @Transactional
    public HousekeepingTaskResponse skip(@PathVariable UUID id) {
        HousekeepingTask entity = repository.findById(id)
                .orElseThrow(() -> new HousekeepingTaskNotFoundException(id));
        if (!entity.getStatus().equals("PENDING")) {
            throw new IllegalStateException(
                    "Status must be 'PENDING' to perform this transition, current is '" + entity.getStatus() + "'");
        }
        entity.setStatus("SKIPPED");
        return HousekeepingTaskResponse.from(repository.save(entity));
    }
}
