package com.synth.hotelhousekeepingservice.housekeepingTask;

public final class HousekeepingTaskMapper {

    private HousekeepingTaskMapper() {}

    public static HousekeepingTask toEntity(HousekeepingTaskCreateRequest request) {
        HousekeepingTask entity = new HousekeepingTask();
        entity.setHotelId(request.hotelId());
        entity.setRoomId(request.roomId());
        entity.setTaskType(request.taskType());
        entity.setPriority(request.priority());
        entity.setStatus(request.status());
        entity.setScheduledDate(request.scheduledDate());
        entity.setCompletedAt(request.completedAt());
        entity.setNotes(request.notes());
        return entity;
    }

    public static void updateEntity(HousekeepingTask entity, HousekeepingTaskUpdateRequest request) {
        entity.setHotelId(request.hotelId());
        entity.setRoomId(request.roomId());
        entity.setTaskType(request.taskType());
        entity.setPriority(request.priority());
        entity.setStatus(request.status());
        entity.setScheduledDate(request.scheduledDate());
        entity.setCompletedAt(request.completedAt());
        entity.setNotes(request.notes());
    }
}
