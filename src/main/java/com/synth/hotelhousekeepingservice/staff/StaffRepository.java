package com.synth.hotelhousekeepingservice.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID>, JpaSpecificationExecutor<Staff> {
    @org.springframework.data.jpa.repository.Query(
            nativeQuery = true,
            value = "SELECT * FROM staff WHERE (setweight(to_tsvector('english', coalesce(first_name, '')), 'A') || setweight(to_tsvector('english', coalesce(last_name, '')), 'A') || setweight(to_tsvector('english', coalesce(email, '')), 'B')) @@ plainto_tsquery('english', :q) ORDER BY ts_rank((setweight(to_tsvector('english', coalesce(first_name, '')), 'A') || setweight(to_tsvector('english', coalesce(last_name, '')), 'A') || setweight(to_tsvector('english', coalesce(email, '')), 'B')), plainto_tsquery('english', :q)) DESC")
    java.util.List<Staff> searchStaff(@org.springframework.data.repository.query.Param("q") String q);
}
