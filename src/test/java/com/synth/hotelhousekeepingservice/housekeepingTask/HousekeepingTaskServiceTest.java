package com.synth.hotelhousekeepingservice.housekeepingTask;

import com.synth.hotelhousekeepingservice.exception.HousekeepingTaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.Instant;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.synth.hotelhousekeepingservice.staff.Staff;
import com.synth.hotelhousekeepingservice.staff.StaffRepository;

@ExtendWith(MockitoExtension.class)
class HousekeepingTaskServiceTest {

    @Mock
    private HousekeepingTaskRepository repository;
    @Mock
    private StaffRepository staffRepository;
    @Mock
    private HousekeepingTaskProcessingStrategy strategy;
    private HousekeepingTaskStrategyFactory strategyFactory;
    private HousekeepingTaskService service;

    private static final UUID EXISTING_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID MISSING_ID  = UUID.fromString("22222222-2222-2222-2222-222222222222");

    private HousekeepingTask fixture;

    @BeforeEach
    void seedFixture() {
        fixture = new HousekeepingTask();
        
        ReflectionTestUtils.setField(fixture, "id", EXISTING_ID);
        fixture.setHotelId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        fixture.setRoomId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        fixture.setTaskType("sample-task-type");
        fixture.setPriority("sample-priority");
        fixture.setStatus("sample-status");
        fixture.setCompletedAt(Instant.parse("2024-01-01T00:00:00Z"));
        fixture.setNotes("sample-notes");
        strategyFactory = new HousekeepingTaskStrategyFactory(List.of(strategy));
        service = new HousekeepingTaskService(repository, staffRepository, strategyFactory);
    }

    // ── findAll ────────────────────────────────────────────────────────────────

    @Test
    void should_return_all_responses_when_repository_has_entities() {
        Pageable pageable = PageRequest.of(0, 20);
        when(repository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(fixture)));
        Page<HousekeepingTaskResponse> result = service.findAll(new HousekeepingTaskFilterRequest(), pageable);
        assertThat(result.getContent()).hasSize(1);
    }

    // ── findById ───────────────────────────────────────────────────────────────

    @Test
    void should_return_response_when_entity_exists_by_id() {
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));

        HousekeepingTaskResponse result = service.findById(EXISTING_ID);

        assertThat(result.id()).isEqualTo(EXISTING_ID);
        verify(repository).findById(EXISTING_ID);
    }

    @Test
    void should_throw_entity_not_found_when_id_is_unknown() {
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(MISSING_ID))
                .isInstanceOf(HousekeepingTaskNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));
    }

    // ── create ─────────────────────────────────────────────────────────────────

    @Test
    void should_persist_entity_and_return_response_when_create_is_called() {
        HousekeepingTaskCreateRequest request = new HousekeepingTaskCreateRequest(UUID.fromString("11111111-1111-1111-1111-111111111111"), UUID.fromString("11111111-1111-1111-1111-111111111111"), "test-value", "test-value", "test-value", java.time.LocalDate.of(2024, 1, 1), java.time.Instant.parse("2024-01-01T00:00:00Z"), "test-value", UUID.fromString("11111111-1111-1111-1111-111111111111"));
        when(staffRepository.findById(any())).thenReturn(Optional.of(new Staff()));
        when(strategy.supports(any())).thenReturn(true);
        when(repository.save(any(HousekeepingTask.class))).thenReturn(fixture);

        HousekeepingTaskResponse result = service.create(request);

        assertThat(result.id()).isEqualTo(fixture.getId());
        assertThat(result.hotelId()).isEqualTo(fixture.getHotelId());
        
        ArgumentCaptor<HousekeepingTask> createCaptor = ArgumentCaptor.forClass(HousekeepingTask.class);
        verify(repository).save(createCaptor.capture());
        assertThat(createCaptor.getValue().getHotelId()).isEqualTo(request.hotelId());
        
    }

    // ── update ─────────────────────────────────────────────────────────────────

    @Test
    void should_update_entity_and_return_response_when_entity_exists() {
        HousekeepingTaskUpdateRequest request = new HousekeepingTaskUpdateRequest(UUID.fromString("22222222-2222-2222-2222-222222222222"), UUID.fromString("22222222-2222-2222-2222-222222222222"), "updated-value", "updated-value", "updated-value", java.time.LocalDate.of(2025, 6, 1), java.time.Instant.parse("2025-06-01T00:00:00Z"), "updated-value", UUID.fromString("22222222-2222-2222-2222-222222222222"));
        when(staffRepository.findById(any())).thenReturn(Optional.of(new Staff()));
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));
        when(repository.save(any(HousekeepingTask.class))).thenReturn(fixture);

        HousekeepingTaskResponse result = service.update(EXISTING_ID, request);

        assertThat(result.id()).isEqualTo(EXISTING_ID);
        assertThat(result.hotelId()).isEqualTo(fixture.getHotelId());
        verify(repository).findById(EXISTING_ID);
        ArgumentCaptor<HousekeepingTask> updateCaptor = ArgumentCaptor.forClass(HousekeepingTask.class);
        verify(repository).save(updateCaptor.capture());
        assertThat(updateCaptor.getValue().getHotelId()).isEqualTo(request.hotelId());
        
    }

    @Test
    void should_throw_entity_not_found_when_updating_with_unknown_id() {
        HousekeepingTaskUpdateRequest request = new HousekeepingTaskUpdateRequest(UUID.fromString("22222222-2222-2222-2222-222222222222"), UUID.fromString("22222222-2222-2222-2222-222222222222"), "updated-value", "updated-value", "updated-value", java.time.LocalDate.of(2025, 6, 1), java.time.Instant.parse("2025-06-01T00:00:00Z"), "updated-value", UUID.fromString("22222222-2222-2222-2222-222222222222"));
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(MISSING_ID, request))
                .isInstanceOf(HousekeepingTaskNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));

        verify(repository).findById(MISSING_ID);
        verify(repository, never()).save(any(HousekeepingTask.class));
    }

    // ── delete ─────────────────────────────────────────────────────────────────

    @Test
    void should_remove_entity_when_id_exists() {
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));

        service.delete(EXISTING_ID);

        verify(repository).findById(EXISTING_ID);
        verify(repository).delete(fixture);
    }

    @Test
    void should_throw_entity_not_found_when_deleting_with_unknown_id() {
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(MISSING_ID))
                .isInstanceOf(HousekeepingTaskNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));

        verify(repository, never()).delete(any(HousekeepingTask.class));
    }
}
