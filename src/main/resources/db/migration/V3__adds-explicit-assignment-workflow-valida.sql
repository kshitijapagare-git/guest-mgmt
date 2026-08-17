ALTER TABLE "staff" RENAME COLUMN "first_name" TO "first_name";
ALTER TABLE "staff" RENAME COLUMN "last_name" TO "last_name";
DO $$ BEGIN
  CREATE TYPE "staff_role_enum" AS ENUM ('HOUSEKEEPER', 'SUPERVISOR', 'MAINTENANCE_TECH', 'MANAGER');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
ALTER TABLE "staff" ADD COLUMN "role" staff_role_enum NOT NULL;
DO $$ BEGIN
  CREATE TYPE "staff_status_enum" AS ENUM ('ACTIVE', 'ON_LEAVE', 'INACTIVE');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
ALTER TABLE "staff" ADD COLUMN "status" staff_status_enum NOT NULL;
ALTER TABLE "staff" ALTER COLUMN "email" SET NOT NULL;
ALTER TABLE "staff" ALTER COLUMN "phone" SET NOT NULL;
ALTER TABLE "housekeeping_tasks" RENAME COLUMN "completed_at" TO "completed_at";
ALTER TABLE "housekeeping_tasks" RENAME COLUMN "hotel_id" TO "hotel_id";
ALTER TABLE "housekeeping_tasks" RENAME COLUMN "room_id" TO "room_id";
ALTER TABLE "housekeeping_tasks" RENAME COLUMN "scheduled_date" TO "scheduled_date";
DO $$ BEGIN
  CREATE TYPE "housekeeping_tasks_priority_enum" AS ENUM ('LOW', 'NORMAL', 'HIGH', 'URGENT');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
ALTER TABLE "housekeeping_tasks" ADD COLUMN "priority" housekeeping_tasks_priority_enum NOT NULL;
DO $$ BEGIN
  CREATE TYPE "housekeeping_tasks_status_enum" AS ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'SKIPPED');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
ALTER TABLE "housekeeping_tasks" ADD COLUMN "status" housekeeping_tasks_status_enum NOT NULL;
DO $$ BEGIN
  CREATE TYPE "housekeeping_tasks_task_type_enum" AS ENUM ('CLEANING', 'DEEP_CLEANING', 'LINEN_CHANGE', 'RESTOCK_AMENITIES', 'INSPECTION');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
ALTER TABLE "housekeeping_tasks" ADD COLUMN "task_type" housekeeping_tasks_task_type_enum NOT NULL;
ALTER TABLE "maintenance_requests" RENAME COLUMN "estimated_cost" TO "estimated_cost";
ALTER TABLE "maintenance_requests" RENAME COLUMN "hotel_id" TO "hotel_id";
ALTER TABLE "maintenance_requests" RENAME COLUMN "reported_by_guest_id" TO "reported_by_guest_id";
ALTER TABLE "maintenance_requests" RENAME COLUMN "resolved_at" TO "resolved_at";
ALTER TABLE "maintenance_requests" RENAME COLUMN "room_id" TO "room_id";
DO $$ BEGIN
  CREATE TYPE "maintenance_requests_issue_type_enum" AS ENUM ('PLUMBING', 'ELECTRICAL', 'HVAC', 'FURNITURE', 'APPLIANCE', 'OTHER');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
ALTER TABLE "maintenance_requests" ADD COLUMN "issue_type" maintenance_requests_issue_type_enum NOT NULL;
DO $$ BEGIN
  CREATE TYPE "maintenance_requests_priority_enum" AS ENUM ('LOW', 'NORMAL', 'HIGH', 'URGENT');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
ALTER TABLE "maintenance_requests" ADD COLUMN "priority" maintenance_requests_priority_enum NOT NULL;
DO $$ BEGIN
  CREATE TYPE "maintenance_requests_status_enum" AS ENUM ('OPEN', 'ASSIGNED', 'IN_PROGRESS', 'RESOLVED', 'CLOSED');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;
ALTER TABLE "maintenance_requests" ADD COLUMN "status" maintenance_requests_status_enum NOT NULL;
ALTER TABLE "maintenance_requests" ALTER COLUMN "description" SET NOT NULL;