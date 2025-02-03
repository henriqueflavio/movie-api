package pt.xpandit.movieapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.xpandit.movieapi.domain.Movie;
import pt.xpandit.movieapi.dto.request.MovieRequest;
import pt.xpandit.movieapi.dto.response.MovieResponse;
import pt.xpandit.movieapi.exception.MovieNotFoundException;
import pt.xpandit.movieapi.service.MovieService;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing movie resources in the application.
 * Provides endpoints for CRUD operations and filtering movies.
 */
@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
@Tag(name = "Movies", description = "Movie management API")
public class MovieController {

    private final MovieService movieService;

    /**
     * Creates a new movie in the system.
     *
     * @param movieRequest the movie information to be created
     *
     * @return ResponseEntity containing the created movie and HTTP 201 status
     */
    @PostMapping
    @Operation(summary = "Create a new movie")
    public ResponseEntity<Movie> create(@RequestBody @Valid MovieRequest movieRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(movieRequest));
    }

    /**
     * Retrieves all movies from the system.
     *
     * @return ResponseEntity containing a list of all movies and HTTP 200 status
     *         Returns an empty list if no movies are found
     */
    @GetMapping
    @Operation(summary = "List all movies")
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findAll());
    }

    /**
     * Retrieves movies within a specified launch date range.
     *
     * @param startDate the start date of the range
     *                  must be in ISO date format (yyyy-MM-dd)
     * @param endDate   the end date of the range
     *                  must be in ISO date format (yyyy-MM-dd)
     * @return ResponseEntity containing filtered list of movies and HTTP 200 status
     *         Returns an empty list if no movies are found in the date range
     * @throws IllegalArgumentException if endDate is before startDate
     */
    @GetMapping("/filter")
    @Operation(summary = "Filter movies by the launch date range")
    public ResponseEntity<List<MovieResponse>> findByLaunchDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(movieService.findByLaunchDateRange(startDate, endDate));
    }

    /**
     * Retrieves a specific movie by its ID.
     *
     * @param id the unique identifier of the movie
     * @return ResponseEntity containing the requested movie and HTTP 200 status
     * @throws MovieNotFoundException if no movie is found with the given ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get an existing movie")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findById(id));
    }

    /**
     * Updates an existing movie's information.
     *
     * @param id           the unique identifier of the movie to update
     * @param movieRequest the updated movie information
     *
     * @return ResponseEntity containing the updated movie and HTTP 200 status
     * @throws MovieNotFoundException if no movie is found with the given ID
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing movie")
    public ResponseEntity<Object> update(@PathVariable(value = "id") Long id,
                                         @RequestBody @Valid MovieRequest movieRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.update(id, movieRequest));
    }

    /**
     * Deletes a movie from the system.
     *
     * @param id the unique identifier of the movie to delete
     * @return ResponseEntity with a success message and HTTP 200 status
     * @throws MovieNotFoundException if no movie is found with the given ID
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        movieService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Movie deleted successfully.");
    }

}
