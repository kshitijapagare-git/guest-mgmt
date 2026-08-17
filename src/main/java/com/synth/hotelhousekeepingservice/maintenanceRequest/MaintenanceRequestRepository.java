package com.synth.hotelhousekeepingservice.maintenanceRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, UUID>, JpaSpecificationExecutor<MaintenanceRequest> {
    @org.springframework.data.jpa.repository.Query(
            nativeQuery = true,
            value = "SELECT * FROM maintenance_requests WHERE (setweight(to_tsvector('english', coalesce(description, '')), 'A')) @@ plainto_tsquery('english', :q) ORDER BY ts_rank((setweight(to_tsvector('english', coalesce(description, '')), 'A')), plainto_tsquery('english', :q)) DESC")
    java.util.List<MaintenanceRequest> searchMaintenanceRequests(@org.springframework.data.repository.query.Param("q") String q);
}
