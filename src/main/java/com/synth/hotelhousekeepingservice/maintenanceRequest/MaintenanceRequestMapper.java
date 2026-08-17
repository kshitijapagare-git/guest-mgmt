package com.synth.hotelhousekeepingservice.maintenanceRequest;

public final class MaintenanceRequestMapper {

    private MaintenanceRequestMapper() {}

    public static MaintenanceRequest toEntity(MaintenanceRequestCreateRequest request) {
        MaintenanceRequest entity = new MaintenanceRequest();
        entity.setHotelId(request.hotelId());
        entity.setRoomId(request.roomId());
        entity.setReportedByGuestId(request.reportedByGuestId());
        entity.setIssueType(request.issueType());
        entity.setDescription(request.description());
        entity.setPriority(request.priority());
        entity.setStatus(request.status());
        entity.setEstimatedCost(request.estimatedCost());
        entity.setResolvedAt(request.resolvedAt());
        return entity;
    }

    public static void updateEntity(MaintenanceRequest entity, MaintenanceRequestUpdateRequest request) {
        entity.setHotelId(request.hotelId());
        entity.setRoomId(request.roomId());
        entity.setReportedByGuestId(request.reportedByGuestId());
        entity.setIssueType(request.issueType());
        entity.setDescription(request.description());
        entity.setPriority(request.priority());
        entity.setStatus(request.status());
        entity.setEstimatedCost(request.estimatedCost());
        entity.setResolvedAt(request.resolvedAt());
    }
}
