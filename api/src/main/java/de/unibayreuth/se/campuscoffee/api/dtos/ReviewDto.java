package de.unibayreuth.se.campuscoffee.api.dtos;

import de.unibayreuth.se.campuscoffee.domain.model.CampusType;
import de.unibayreuth.se.campuscoffee.domain.model.PosType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private Long posId;
    @NotBlank(message = "Review message cannot be empty.")
    private final String review;
    //@NotNull
    private final Boolean approved;
}
