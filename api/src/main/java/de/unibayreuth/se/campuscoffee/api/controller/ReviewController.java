package de.unibayreuth.se.campuscoffee.api.controller;

import de.unibayreuth.se.campuscoffee.api.dtos.ReviewDto;
import de.unibayreuth.se.campuscoffee.api.dtos.UserDto;
import de.unibayreuth.se.campuscoffee.api.mapper.ReviewDtoMapper;
import de.unibayreuth.se.campuscoffee.api.mapper.UserDtoMapper;
import de.unibayreuth.se.campuscoffee.domain.exceptions.PosNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.ReviewNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.ports.PosService;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "CampusCoffee",
                version = "0.0.1"
        )
)
@Tag(name = "Review")
@Controller
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewDtoMapper reviewDtoMapper;
    private final UserDtoMapper userDtoMapper;
    private final PosService posService;

    @Operation(
            summary = "Get all Reviews.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = ReviewDto.class)
                            ),
                            description = "All Reviews as a JSON array."
                    )
            }
    )
    @GetMapping("")
    public ResponseEntity<List<ReviewDto>> getAll() {
        return ResponseEntity.ok(
                reviewService.getAll().stream()
                        .map(reviewDtoMapper::fromDomain)
                        .toList()
        );
    }

    @Operation(
            summary = "Get Review by ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReviewDto.class)
                            ),
                            description = "The Review with the provided ID as a JSON object."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No Review with the provided ID could be found."
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    reviewDtoMapper.fromDomain(reviewService.getById(id))
            );
        } catch (PosNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Get approved Reviews by pos ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReviewDto.class)
                            ),
                            description = "The approved Reviews with the provided pos ID as a JSON object."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No Review with the provided pos ID could be found."
                    )
            }
    )
    @GetMapping("/filter")
    public ResponseEntity<List<ReviewDto>> getApprovedByPosId(@RequestParam("pos_id") Long posId) {
        try {
            return ResponseEntity.ok(
                    reviewService.getApprovedByPos(posService.getById(posId)).stream()
                            .map(reviewDtoMapper::fromDomain)
                            .toList()
            );
        } catch (PosNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Operation(
            summary = "Creates a new Review.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReviewDto.class)
                            ),
                            description = "The new Review as a JSON object."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID is not empty."
                    )
            }
    )
    @PostMapping("")
    public ResponseEntity<ReviewDto> create(@RequestBody @Valid ReviewDto reviewDto) {
        return ResponseEntity.ok(
                reviewDtoMapper.fromDomain(reviewService.create(reviewDtoMapper.toDomain(reviewDto)))
        );
    }

    @Operation(
            summary = "Approve an existing Review.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReviewDto.class)
                            ),
                            description = "The approved Review as a JSON object."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "IDs in path and body do not match or no Review with the provided ID could be found."
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> update(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        try {
            return ResponseEntity.ok(
                    reviewDtoMapper.fromDomain(
                            reviewService.approve(
                                    reviewService.getById(id), userDtoMapper.toDomain(userDto)
                            )
                    )
            );
        } catch (ReviewNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
