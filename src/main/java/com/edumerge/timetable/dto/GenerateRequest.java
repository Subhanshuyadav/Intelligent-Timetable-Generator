package com.edumerge.timetable.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
public record GenerateRequest(@Min(1) @Max(7) int days,@Min(1) @Max(12) int periodsPerDay) {
    public GenerateRequest(){this(5,7);}
}
