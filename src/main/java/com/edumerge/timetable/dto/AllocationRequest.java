package com.edumerge.timetable.dto;
import jakarta.validation.constraints.NotNull;
public record AllocationRequest(@NotNull Long divisionId,@NotNull Long subjectId,@NotNull Long teacherId) {}
