CREATE TABLE IF NOT EXISTS staff (
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    role staff_role_enum NOT NULL,
    status staff_status_enum NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS housekeeping_tasks (
    id UUID NOT NULL PRIMARY KEY,
    hotel_id UUID NOT NULL,
    room_id UUID NOT NULL,
    task_type housekeeping_tasks_task_type_enum NOT NULL,
    priority housekeeping_tasks_priority_enum NOT NULL,
    status housekeeping_tasks_status_enum NOT NULL,
    scheduled_date DATE NOT NULL,
    completed_at TIMESTAMPTZ,
    notes VARCHAR(500),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_housekeeping_tasks_assigned_staff_id FOREIGN KEY (assigned_staff_id) REFERENCES staffs(id)
);
CREATE TABLE IF NOT EXISTS maintenance_requests (
    id UUID NOT NULL PRIMARY KEY,
    hotel_id UUID NOT NULL,
    room_id UUID NOT NULL,
    reported_by_guest_id UUID,
    issue_type maintenance_requests_issue_type_enum NOT NULL,
    description VARCHAR(1000) NOT NULL,
    priority maintenance_requests_priority_enum NOT NULL,
    status maintenance_requests_status_enum NOT NULL,
    estimated_cost NUMERIC,
    resolved_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_maintenance_requests_assigned_technician_id FOREIGN KEY (assigned_technician_id) REFERENCES staffs(id)
);