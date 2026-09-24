# Intelligent Timetable Generator — Java 25

A working MVP for the Edumerge Product Engineering Assignment 3. The assignment asks for timetable generation across divisions, subjects, faculty and classrooms while respecting teacher/classroom/division conflicts, room requirements, scheduling relationships, and impossible constraints. fileciteturn0file0L36-L42

## Stack
- Java 25
- Spring Boot 4.1.1
- Spring Web / Validation
- Spring Data JPA
- H2 file database
- HTML/CSS/JavaScript demo UI

Spring Boot 4.1.1 is documented as compatible with Java versions up to 26, so Java 25 is supported. See the official Spring Boot system requirements. 

## Run
1. Install JDK 25 and Maven 3.9+.
2. In this folder run:
   `mvn clean test`
3. Run:
   `mvn spring-boot:run`
4. Open `http://localhost:8080`
5. H2 console: `http://localhost:8080/h2-console`
   JDBC URL: `jdbc:h2:file:./data/timetabledb`
   User: `sa`, Password: blank.

## API
GET `/api/divisions`, `/api/teachers`, `/api/rooms`, `/api/subjects`, `/api/allocations`, `/api/timetable`
POST `/api/divisions`
POST `/api/teachers`
POST `/api/rooms`
POST `/api/subjects`
POST `/api/allocations`
POST `/api/generate` body: `{"days":5,"periodsPerDay":7}`

## Hard constraints implemented
1. One division cannot have two classes in the same slot.
2. One teacher cannot teach two classes in the same slot.
3. One room cannot host two classes in the same slot.
4. Room capacity must cover division size.
5. Lab subjects must use lab rooms.
6. If constraints cannot be satisfied, the API returns a clear feasibility error instead of silently producing an invalid timetable.

## Architecture
Controller -> Service -> Repository -> Database.
The generator uses constraint satisfaction with backtracking and a constrained-first ordering. This is intentionally simple enough to explain in an interview while being extensible toward OR-Tools/OptaPlanner-style optimization later.

## Demo talking points
- Hard constraints are never violated.
- The generator explicitly detects infeasible inputs.
- The current UI is an MVP; production additions could include authentication/RBAC, drag-and-drop editing, soft constraints, audit history, PostgreSQL, and export to PDF/Excel.
