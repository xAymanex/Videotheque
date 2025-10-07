package com.project.videotheque.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class MovieRequest {
    @NotBlank public String title;
    @Min(1888) @Max(2100) public int year;
    @Positive public int durationMinutes;

    @NotNull public Long directorId;
    @NotNull public Long genreId;

    @NotEmpty(message = "actorIds must contain at least one actor id")
    public List<Long> actorIds;
}
