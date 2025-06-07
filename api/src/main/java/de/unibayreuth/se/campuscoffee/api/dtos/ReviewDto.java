package de.unibayreuth.se.campuscoffee.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for Review metadata.
 *
 */
@Data
@Builder(toBuilder = true)
public class ReviewDto {
    private Long id; // id is null when creating a new task
    private LocalDateTime createdAt; // is null when using DTO to create a new POS
    //@NotBlank(message = "Name cannot be empty.")
    //@Size(max = 255, message = "Name can be at most 255 characters long.")
    private final Long posId;
    private final Long authorId;
    @NotBlank(message = "Review message cannot be empty.")
    private final String review;
    private final Boolean approved;
}
