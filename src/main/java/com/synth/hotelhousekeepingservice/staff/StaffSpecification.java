package com.synth.hotelhousekeepingservice.staff;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class StaffSpecification {

    public static Specification<Staff> build(StaffFilterRequest filter) {
        return Specification.where(role(filter))
                .and(status(filter))
                .and(search(filter));
    }

    private static Specification<Staff> role(StaffFilterRequest filter) {
        return (root, query, cb) -> filter.role() == null ? null :
                cb.equal(root.get("role"), filter.role());
    }

    private static Specification<Staff> status(StaffFilterRequest filter) {
        return (root, query, cb) -> filter.status() == null ? null :
                cb.equal(root.get("status"), filter.status());
    }

    private static Specification<Staff> search(StaffFilterRequest filter) {
        return (root, query, cb) -> filter.search() == null ? null :
                cb.like(cb.lower(root.get("lastName")), "%"+filter.search().toLowerCase()+"%");
    }
}
