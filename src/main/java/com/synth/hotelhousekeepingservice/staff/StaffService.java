package com.synth.hotelhousekeepingservice.staff;

import com.synth.hotelhousekeepingservice.exception.StaffNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
@Service
public class StaffService {

    private final StaffRepository repository;
    private final StaffStrategyFactory strategyFactory;
    public Page<StaffResponse> findAll(StaffFilterRequest filter, Pageable pageable) {
        return repository.findAll(StaffSpecification.build(filter), pageable)
                .map(StaffResponse::from);
    }
    public StaffResponse findById(UUID id) {
        Staff entity = repository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException(id));
        return StaffResponse.from(entity);
    }
    public StaffResponse create(StaffCreateRequest request) {
        Staff entity = StaffMapper.toEntity(request);
        
        
        
        
        
        
        
        strategyFactory.resolve(entity.getRole()).execute(entity);
        StaffResponse response = StaffResponse.from(repository.save(entity));
        return response;
    }
    public StaffResponse update(UUID id, StaffUpdateRequest request) {
        Staff entity = repository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException(id));
        StaffMapper.updateEntity(entity, request);
        
        
        
        
        
        
        
        StaffResponse response = StaffResponse.from(repository.save(entity));
        return response;
    }
    public void delete(UUID id) {
        Staff entity = repository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException(id));
        repository.delete(entity);
    }

}
