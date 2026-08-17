package <no value>.maintenance.request.assignment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.Instant;
import java.util.*;

import <no value>.maintenance.request.assignment.dto.MaintenanceRequestCreateDto;
import <no value>.maintenance.request.assignment.dto.MaintenanceRequestDto;

import <no value>.maintenance.request.assignment.model.MaintenanceRequest;
import <no value>.maintenance.request.assignment.model.Staff;
import <no value>.maintenance.request.assignment.model.StaffRole;

import org.springframework.data.jpa.repository.Query;

// This repository owns direct database access.
@Repository
public class MaintenanceRequestAssignmentWorkflowRepository {

    private final MaintenanceRequestJpaRepository maintenanceRequestJpaRepository;
    private final StaffJpaRepository staffJpaRepository;

    public MaintenanceRequestAssignmentWorkflowRepository(MaintenanceRequestJpaRepository maintenanceRequestJpaRepository,
                                                          StaffJpaRepository staffJpaRepository) {
        this.maintenanceRequestJpaRepository = maintenanceRequestJpaRepository;
        this.staffJpaRepository = staffJpaRepository;
    }

    @Transactional
    public MaintenanceRequestDto createMaintenanceRequest(MaintenanceRequestCreateDto dto, UUID hotelId, UUID reportedByGuestId) {
        MaintenanceRequest entity = new MaintenanceRequest();
        entity.setHotelId(hotelId);
        entity.setRoomId(dto.getRoomId());
        entity.setReportedByGuestId(reportedByGuestId);
        entity.setIssueType(dto.getIssueType());
        entity.setDescription(dto.getDescription());
        entity.setPriority(dto.getPriority());
        entity.setStatus(MaintenanceRequest.Status.OPEN);
        entity.setEstimatedCost(dto.getEstimatedCost());

        MaintenanceRequest saved = maintenanceRequestJpaRepository.save(entity);
        return MaintenanceRequestDto.from(saved);
    }

    @Transactional
    public MaintenanceRequestDto assignTechnician(UUID requestId, UUID technicianId) {
        MaintenanceRequest request = maintenanceRequestJpaRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("request_not_found"));

        // Eligibility: only maintenance-capable roles (config-driven via StaffRole enum; see model)
        Optional<Staff> technicianOpt = findTechnicianEligible(technicianId, StaffRole.MAINTENANCE_TECH);
        if (technicianOpt.isEmpty()) {
            throw new IllegalArgumentException("technician_ineligible");
        }

        Staff technician = technicianOpt.get();
        // Transition enforcement: Open -> Assigned
        if (request.getStatus() != MaintenanceRequest.Status.OPEN) {
            throw new IllegalStateException("invalid_status_for_assign");
        }

        request.setAssignedTechnicianId(technician.getId());
        request.setStatus(MaintenanceRequest.Status.ASSIGNED);
        MaintenanceRequest saved = maintenanceRequestJpaRepository.save(request);
        return MaintenanceRequestDto.from(saved);
    }

    @Transactional
    public MaintenanceRequestDto reassignTechnician(UUID requestId, UUID technicianId) {
        MaintenanceRequest request = maintenanceRequestJpaRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("request_not_found"));

        Optional<Staff> technicianOpt = findTechnicianEligible(technicianId, StaffRole.MAINTENANCE_TECH);
        if (technicianOpt.isEmpty()) {
            throw new IllegalArgumentException("technician_ineligible");
        }

        Staff technician = technicianOpt.get();

        // Reassign allowed while in ASSIGNED; status remains ASSIGNED
        if (request.getStatus() != MaintenanceRequest.Status.ASSIGNED) {
            throw new IllegalStateException("invalid_status_for_reassign");
        }

        request.setAssignedTechnicianId(technician.getId());
        request.setStatus(MaintenanceRequest.Status.ASSIGNED);
        MaintenanceRequest saved = maintenanceRequestJpaRepository.save(request);
        return MaintenanceRequestDto.from(saved);
    }

    @Transactional
    public MaintenanceRequestDto startMaintenance(UUID requestId) {
        MaintenanceRequest request = maintenanceRequestJpaRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("request_not_found"));

        if (request.getStatus() != MaintenanceRequest.Status.ASSIGNED) {
            throw new IllegalStateException("invalid_status_for_start");
        }

        request.setStatus(MaintenanceRequest.Status.IN_PROGRESS);
        MaintenanceRequest saved = maintenanceRequestJpaRepository.save(request);
        return MaintenanceRequestDto.from(saved);
    }

    @Transactional
    public MaintenanceRequestDto resolveMaintenance(UUID requestId, BigDecimal estimatedCost, Instant resolvedAt) {
        MaintenanceRequest request = maintenanceRequestJpaRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("request_not_found"));

        if (request.getStatus() != MaintenanceRequest.Status.IN_PROGRESS) {
            throw new IllegalStateException("invalid_status_for_resolve");
        }

        request.setEstimatedCost(estimatedCost);
        request.setResolvedAt(resolvedAt);
        request.setStatus(MaintenanceRequest.Status.RESOLVED);

        MaintenanceRequest saved = maintenanceRequestJpaRepository.save(request);
        return MaintenanceRequestDto.from(saved);
    }

    @Transactional
    public MaintenanceRequestDto closeMaintenance(UUID requestId) {
        MaintenanceRequest request = maintenanceRequestJpaRepository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("request_not_found"));

        if (request.getStatus() != MaintenanceRequest.Status.RESOLVED) {
            throw new IllegalStateException("invalid_status_for_close");
        }

        request.setStatus(MaintenanceRequest.Status.CLOSED);
        MaintenanceRequest saved = maintenanceRequestJpaRepository.save(request);
        return MaintenanceRequestDto.from(saved);
    }

    public Page<MaintenanceRequestDto> searchMaintenanceRequests(String search, UUID hotelId, Pageable pageable) {
        return maintenanceRequestSearch(search, hotelId, pageable).map(MaintenanceRequestDto::from);
    }

    public Page<MaintenanceRequest> maintenanceRequestSearch(String search, UUID hotelId, Pageable pageable) {
        if (hotelId == null) throw new IllegalArgumentException("hotelId_required");
        return maintenanceRequestJpaRepository.searchByHotelAndDescription(search, hotelId, pageable);
    }

    public Optional<MaintenanceRequest> findById(UUID id) {
        return maintenanceRequestJpaRepository.findById(id);
    }

    public MaintenanceRequest save(MaintenanceRequest entity) {
        return maintenanceRequestJpaRepository.save(entity);
    }

    public Optional<Staff> findTechnicianEligible(UUID technicianId, StaffRole role) {
        if (technicianId == null || role == null) return Optional.empty();
        // Eligibility: ACTIVE staff with desired role.
        return staffJpaRepository.findEligibleByIdAndRole(technicianId, role, Staff.Status.ACTIVE);
    }

    // Internal Jpa repositories to keep this file self-contained in DB access.
    public interface MaintenanceRequestJpaRepository extends JpaRepository<MaintenanceRequest, UUID> {
        @Query("select r from MaintenanceRequest r where r.hotelId = :hotelId and (:search is null or lower(r.description) like lower(concat('%', :search, '%'))) order by r.priority desc")
        Page<MaintenanceRequest> searchByHotelAndDescription(String search, UUID hotelId, Pageable pageable);
    }

    public interface StaffJpaRepository extends JpaRepository<Staff, UUID> {
        @Query("select s from Staff s where s.id = :id and s.role = :role and s.status = :status")
        Optional<Staff> findEligibleByIdAndRole(UUID id, StaffRole role, Staff.Status status);
    }
}
