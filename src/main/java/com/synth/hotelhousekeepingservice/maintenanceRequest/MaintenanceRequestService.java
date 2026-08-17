package com.synth.hotelhousekeepingservice.maintenanceRequest;

import com.synth.hotelhousekeepingservice.exception.MaintenanceRequestNotFoundException;
import com.synth.hotelhousekeepingservice.exception.StaffNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.synth.hotelhousekeepingservice.staff.Staff;
import com.synth.hotelhousekeepingservice.staff.StaffRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Service
public class MaintenanceRequestService {

    private final MaintenanceRequestRepository repository;
    private final StaffRepository staffRepository;
    private final MaintenanceRequestStrategyFactory strategyFactory;
    @Transactional(readOnly = true)
    public Page<MaintenanceRequestResponse> findAll(MaintenanceRequestFilterRequest filter, Pageable pageable) {
        return repository.findAll(MaintenanceRequestSpecification.build(filter), pageable)
                .map(MaintenanceRequestResponse::from);
    }
    @Transactional(readOnly = true)
    public MaintenanceRequestResponse findById(UUID id) {
        MaintenanceRequest entity = repository.findById(id)
                .orElseThrow(() -> new MaintenanceRequestNotFoundException(id));
        return MaintenanceRequestResponse.from(entity);
    }
    @Transactional
    public MaintenanceRequestResponse create(MaintenanceRequestCreateRequest request) {
        MaintenanceRequest entity = MaintenanceRequestMapper.toEntity(request);
        
        
        
        
        
        
        
        
        
        
        if (request.assignedTechnicianId() != null) {
            entity.setAssignedTechnicianId(staffRepository.findById(request.assignedTechnicianId())
                    .orElseThrow(() -> new StaffNotFoundException(request.assignedTechnicianId())));
        }
        
        strategyFactory.resolve(entity.getIssueType()).execute(entity);
        MaintenanceRequestResponse response = MaintenanceRequestResponse.from(repository.save(entity));
        return response;
    }
    @Transactional
    public MaintenanceRequestResponse update(UUID id, MaintenanceRequestUpdateRequest request) {
        MaintenanceRequest entity = repository.findById(id)
                .orElseThrow(() -> new MaintenanceRequestNotFoundException(id));
        MaintenanceRequestMapper.updateEntity(entity, request);
        
        
        
        
        
        
        
        
        
        
        if (request.assignedTechnicianId() != null) {
            entity.setAssignedTechnicianId(staffRepository.findById(request.assignedTechnicianId())
                    .orElseThrow(() -> new StaffNotFoundException(request.assignedTechnicianId())));
        }
        
        MaintenanceRequestResponse response = MaintenanceRequestResponse.from(repository.save(entity));
        return response;
    }
    @Transactional
    public void delete(UUID id) {
        MaintenanceRequest entity = repository.findById(id)
                .orElseThrow(() -> new MaintenanceRequestNotFoundException(id));
        repository.delete(entity);
    }

}
