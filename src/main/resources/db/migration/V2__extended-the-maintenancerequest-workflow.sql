DO $$ BEGIN
  CREATE TYPE "staff_role_enum" AS ENUM ('HOUSEKEEPER', 'SUPERVISOR', 'MAINTENANCE_TECH', 'MANAGER');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
DO $$ BEGIN
  CREATE TYPE "staff_status_enum" AS ENUM ('ACTIVE', 'ON_LEAVE', 'INACTIVE');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
CREATE TABLE IF NOT EXISTS "staff" (
  "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  "email" VARCHAR(255) NOT NULL,
  "first_name" VARCHAR(80) NOT NULL,
  "last_name" VARCHAR(80) NOT NULL,
  "phone" VARCHAR(20) NOT NULL,
  "role" staff_role_enum NOT NULL,
  "status" staff_status_enum NOT NULL,
  "created_at" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  "updated_at" TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE UNIQUE INDEX IF NOT EXISTS "uq_staff_email" ON "staff" ("email");
DO $$ BEGIN
  CREATE TYPE "housekeeping_tasks_priority_enum" AS ENUM ('LOW', 'NORMAL', 'HIGH', 'URGENT');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
DO $$ BEGIN
  CREATE TYPE "housekeeping_tasks_status_enum" AS ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'SKIPPED');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
DO $$ BEGIN
  CREATE TYPE "housekeeping_tasks_task_type_enum" AS ENUM ('CLEANING', 'DEEP_CLEANING', 'LINEN_CHANGE', 'RESTOCK_AMENITIES', 'INSPECTION');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
CREATE TABLE IF NOT EXISTS "housekeeping_tasks" (
  "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  "completed_at" TIMESTAMPTZ,
  "hotel_id" UUID NOT NULL,
  "notes" VARCHAR(500),
  "priority" housekeeping_tasks_priority_enum NOT NULL,
  "room_id" UUID NOT NULL,
  "scheduled_date" DATE NOT NULL,
  "status" housekeeping_tasks_status_enum NOT NULL,
  "task_type" housekeeping_tasks_task_type_enum NOT NULL,
  "created_at" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  "updated_at" TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
DO $$ BEGIN
  CREATE TYPE "maintenance_requests_issue_type_enum" AS ENUM ('PLUMBING', 'ELECTRICAL', 'HVAC', 'FURNITURE', 'APPLIANCE', 'OTHER');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
DO $$ BEGIN
  CREATE TYPE "maintenance_requests_priority_enum" AS ENUM ('LOW', 'NORMAL', 'HIGH', 'URGENT');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
DO $$ BEGIN
  CREATE TYPE "maintenance_requests_status_enum" AS ENUM ('OPEN', 'ASSIGNED', 'IN_PROGRESS', 'RESOLVED', 'CLOSED');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
CREATE TABLE IF NOT EXISTS "maintenance_requests" (
  "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  "description" VARCHAR(1000) NOT NULL,
  "estimated_cost" NUMERIC,
  "hotel_id" UUID NOT NULL,
  "issue_type" maintenance_requests_issue_type_enum NOT NULL,
  "priority" maintenance_requests_priority_enum NOT NULL,
  "reported_by_guest_id" UUID,
  "resolved_at" TIMESTAMPTZ,
  "room_id" UUID NOT NULL,
  "status" maintenance_requests_status_enum NOT NULL,
  "created_at" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  "updated_at" TIMESTAMPTZ NOT NULL DEFAULT NOW()
);