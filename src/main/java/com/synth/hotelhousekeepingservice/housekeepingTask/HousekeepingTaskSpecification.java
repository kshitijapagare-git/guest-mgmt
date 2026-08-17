package com.synth.hotelhousekeepingservice.housekeepingTask;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;

public class HousekeepingTaskSpecification {

    public static Specification<HousekeepingTask> build(HousekeepingTaskFilterRequest filter) {
        return Specification.where(hotelId(filter))
                .and(roomId(filter))
                .and(assignedStaffId(filter))
                .and(status(filter))
                .and(taskType(filter));
    }

    private static Specification<HousekeepingTask> hotelId(HousekeepingTaskFilterRequest filter) {
        return (root, query, cb) -> filter.hotelId() == null ? null :
                cb.equal(root.get("hotelId"), filter.hotelId());
    }

    private static Specification<HousekeepingTask> roomId(HousekeepingTaskFilterRequest filter) {
        return (root, query, cb) -> filter.roomId() == null ? null :
                cb.equal(root.get("roomId"), filter.roomId());
    }

    private static Specification<HousekeepingTask> assignedStaffId(HousekeepingTaskFilterRequest filter) {
        return (root, query, cb) -> filter.assignedStaffId() == null ? null :
                cb.equal(root.get("assignedStaffId"), filter.assignedStaffId());
    }

    private static Specification<HousekeepingTask> status(HousekeepingTaskFilterRequest filter) {
        return (root, query, cb) -> filter.status() == null ? null :
                cb.equal(root.get("status"), filter.status());
    }

    private static Specification<HousekeepingTask> taskType(HousekeepingTaskFilterRequest filter) {
        return (root, query, cb) -> filter.taskType() == null ? null :
                cb.equal(root.get("taskType"), filter.taskType());
    }
}
