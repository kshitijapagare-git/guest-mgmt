package com.synth.hotelhousekeepingservice.staff;

public final class StaffMapper {

    private StaffMapper() {}

    public static Staff toEntity(StaffCreateRequest request) {
        Staff entity = new Staff();
        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setRole(request.role());
        entity.setStatus(request.status());
        return entity;
    }

    public static void updateEntity(Staff entity, StaffUpdateRequest request) {
        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setRole(request.role());
        entity.setStatus(request.status());
    }
}
