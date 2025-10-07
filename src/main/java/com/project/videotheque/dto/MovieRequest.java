package com.project.videotheque.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class MovieRequest {
    @NotBlank public String title;
    @Min(1888) @Max(2100) public int year;
    @Positive public int durationMinutes;
    @NotNull public Long directorId;
    public List<Long> actorIds;
    public List<Long> genreIds;
}
