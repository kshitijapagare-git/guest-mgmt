package com.synth.hotelhousekeepingservice.housekeepingTask;

import com.synth.hotelhousekeepingservice.exception.HousekeepingTaskNotFoundException;
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
public class HousekeepingTaskService {

    private final HousekeepingTaskRepository repository;
    private final StaffRepository staffRepository;
    private final HousekeepingTaskStrategyFactory strategyFactory;
    @Transactional(readOnly = true)
    public Page<HousekeepingTaskResponse> findAll(HousekeepingTaskFilterRequest filter, Pageable pageable) {
        return repository.findAll(HousekeepingTaskSpecification.build(filter), pageable)
                .map(HousekeepingTaskResponse::from);
    }
    @Transactional(readOnly = true)
    public HousekeepingTaskResponse findById(UUID id) {
        HousekeepingTask entity = repository.findById(id)
                .orElseThrow(() -> new HousekeepingTaskNotFoundException(id));
        return HousekeepingTaskResponse.from(entity);
    }
    @Transactional
    public HousekeepingTaskResponse create(HousekeepingTaskCreateRequest request) {
        HousekeepingTask entity = HousekeepingTaskMapper.toEntity(request);
        
        
        
        
        
        
        
        
        
        if (request.assignedStaffId() != null) {
            entity.setAssignedStaffId(staffRepository.findById(request.assignedStaffId())
                    .orElseThrow(() -> new StaffNotFoundException(request.assignedStaffId())));
        }
        
        strategyFactory.resolve(entity.getTaskType()).execute(entity);
        HousekeepingTaskResponse response = HousekeepingTaskResponse.from(repository.save(entity));
        return response;
    }
    @Transactional
    public HousekeepingTaskResponse update(UUID id, HousekeepingTaskUpdateRequest request) {
        HousekeepingTask entity = repository.findById(id)
                .orElseThrow(() -> new HousekeepingTaskNotFoundException(id));
        HousekeepingTaskMapper.updateEntity(entity, request);
        
        
        
        
        
        
        
        
        
        if (request.assignedStaffId() != null) {
            entity.setAssignedStaffId(staffRepository.findById(request.assignedStaffId())
                    .orElseThrow(() -> new StaffNotFoundException(request.assignedStaffId())));
        }
        
        HousekeepingTaskResponse response = HousekeepingTaskResponse.from(repository.save(entity));
        return response;
    }
    @Transactional
    public void delete(UUID id) {
        HousekeepingTask entity = repository.findById(id)
                .orElseThrow(() -> new HousekeepingTaskNotFoundException(id));
        repository.delete(entity);
    }

}
