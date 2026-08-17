package com.synth.hotelhousekeepingservice.maintenanceRequest;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;

public class MaintenanceRequestSpecification {

    public static Specification<MaintenanceRequest> build(MaintenanceRequestFilterRequest filter) {
        return Specification.where(hotelId(filter))
                .and(roomId(filter))
                .and(assignedTechnicianId(filter))
                .and(status(filter))
                .and(priority(filter))
                .and(search(filter));
    }

    private static Specification<MaintenanceRequest> hotelId(MaintenanceRequestFilterRequest filter) {
        return (root, query, cb) -> filter.hotelId() == null ? null :
                cb.equal(root.get("hotelId"), filter.hotelId());
    }

    private static Specification<MaintenanceRequest> roomId(MaintenanceRequestFilterRequest filter) {
        return (root, query, cb) -> filter.roomId() == null ? null :
                cb.equal(root.get("roomId"), filter.roomId());
    }

    private static Specification<MaintenanceRequest> assignedTechnicianId(MaintenanceRequestFilterRequest filter) {
        return (root, query, cb) -> filter.assignedTechnicianId() == null ? null :
                cb.equal(root.get("assignedTechnicianId"), filter.assignedTechnicianId());
    }

    private static Specification<MaintenanceRequest> status(MaintenanceRequestFilterRequest filter) {
        return (root, query, cb) -> filter.status() == null ? null :
                cb.equal(root.get("status"), filter.status());
    }

    private static Specification<MaintenanceRequest> priority(MaintenanceRequestFilterRequest filter) {
        return (root, query, cb) -> filter.priority() == null ? null :
                cb.equal(root.get("priority"), filter.priority());
    }

    private static Specification<MaintenanceRequest> search(MaintenanceRequestFilterRequest filter) {
        return (root, query, cb) -> filter.search() == null ? null :
                cb.like(cb.lower(root.get("description")), "%"+filter.search().toLowerCase()+"%");
    }
}
