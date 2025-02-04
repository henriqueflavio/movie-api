package pt.xpandit.movieapi.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents the response data for a movie.
 *
 * @param title      The title of the movie.
 * @param launchDate The launch date of the movie.
 * @param rank       The ranking of the movie.
 * @param revenue    The revenue generated by the movie.
 */
@Builder
public record MovieResponse(String title, LocalDate launchDate, BigDecimal rank, BigDecimal revenue) {
}
