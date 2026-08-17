package com.synth.hotelhousekeepingservice.staff;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/staffs")
@Tag(name = "Staff Search")
public class StaffSearchController {

    private final StaffRepository repository;

    @Transactional(readOnly = true)
    @GetMapping("/search-staff")
    @Operation(summary = "Search staff across name and email")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR','STAFF')")
    public List<StaffResponse> searchStaff(
            @RequestParam(required = false) String search) {
        if (search == null || search.isBlank()) return List.of();
        return repository.searchStaff(search).stream()
                .map(StaffResponse::from)
                .toList();
    }
}
