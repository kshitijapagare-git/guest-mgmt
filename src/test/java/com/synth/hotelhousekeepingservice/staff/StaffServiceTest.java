package com.synth.hotelhousekeepingservice.staff;

import com.synth.hotelhousekeepingservice.exception.StaffNotFoundException;
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
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {

    @Mock
    private StaffRepository repository;
    @Mock
    private StaffProcessingStrategy strategy;
    private StaffStrategyFactory strategyFactory;
    private StaffService service;

    private static final UUID EXISTING_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID MISSING_ID  = UUID.fromString("22222222-2222-2222-2222-222222222222");

    private Staff fixture;

    @BeforeEach
    void seedFixture() {
        fixture = new Staff();
        
        ReflectionTestUtils.setField(fixture, "id", EXISTING_ID);
        fixture.setFirstName("sample-first-name");
        fixture.setLastName("sample-last-name");
        fixture.setEmail("sample-email");
        fixture.setPhone("sample-phone");
        fixture.setRole("sample-role");
        fixture.setStatus("sample-status");
        strategyFactory = new StaffStrategyFactory(List.of(strategy));
        service = new StaffService(repository, strategyFactory);
    }

    // ── findAll ────────────────────────────────────────────────────────────────

    @Test
    void should_return_all_responses_when_repository_has_entities() {
        Pageable pageable = PageRequest.of(0, 20);
        when(repository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(fixture)));
        Page<StaffResponse> result = service.findAll(new StaffFilterRequest(), pageable);
        assertThat(result.getContent()).hasSize(1);
    }

    // ── findById ───────────────────────────────────────────────────────────────

    @Test
    void should_return_response_when_entity_exists_by_id() {
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));

        StaffResponse result = service.findById(EXISTING_ID);

        assertThat(result.id()).isEqualTo(EXISTING_ID);
        verify(repository).findById(EXISTING_ID);
    }

    @Test
    void should_throw_entity_not_found_when_id_is_unknown() {
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(MISSING_ID))
                .isInstanceOf(StaffNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));
    }

    // ── create ─────────────────────────────────────────────────────────────────

    @Test
    void should_persist_entity_and_return_response_when_create_is_called() {
        StaffCreateRequest request = new StaffCreateRequest("test-value", "test-value", "test-value", "test-value", "test-value", "test-value");
        when(strategy.supports(any())).thenReturn(true);
        when(repository.save(any(Staff.class))).thenReturn(fixture);

        StaffResponse result = service.create(request);

        assertThat(result.id()).isEqualTo(fixture.getId());
        assertThat(result.firstName()).isEqualTo(fixture.getFirstName());
        
        ArgumentCaptor<Staff> createCaptor = ArgumentCaptor.forClass(Staff.class);
        verify(repository).save(createCaptor.capture());
        assertThat(createCaptor.getValue().getFirstName()).isEqualTo(request.firstName());
        
    }

    // ── update ─────────────────────────────────────────────────────────────────

    @Test
    void should_update_entity_and_return_response_when_entity_exists() {
        StaffUpdateRequest request = new StaffUpdateRequest("updated-value", "updated-value", "updated-value", "updated-value", "updated-value", "updated-value");
        when(repository.findById(EXISTING_ID)).thenReturn(Optional.of(fixture));
        when(repository.save(any(Staff.class))).thenReturn(fixture);

        StaffResponse result = service.update(EXISTING_ID, request);

        assertThat(result.id()).isEqualTo(EXISTING_ID);
        assertThat(result.firstName()).isEqualTo(fixture.getFirstName());
        verify(repository).findById(EXISTING_ID);
        ArgumentCaptor<Staff> updateCaptor = ArgumentCaptor.forClass(Staff.class);
        verify(repository).save(updateCaptor.capture());
        assertThat(updateCaptor.getValue().getFirstName()).isEqualTo(request.firstName());
        
    }

    @Test
    void should_throw_entity_not_found_when_updating_with_unknown_id() {
        StaffUpdateRequest request = new StaffUpdateRequest("updated-value", "updated-value", "updated-value", "updated-value", "updated-value", "updated-value");
        when(repository.findById(MISSING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(MISSING_ID, request))
                .isInstanceOf(StaffNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));

        verify(repository).findById(MISSING_ID);
        verify(repository, never()).save(any(Staff.class));
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
                .isInstanceOf(StaffNotFoundException.class)
                .hasMessageContaining(String.valueOf(MISSING_ID));

        verify(repository, never()).delete(any(Staff.class));
    }
}
