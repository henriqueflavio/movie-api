package pt.xpandit.movieapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.xpandit.movieapi.domain.Movie;
import pt.xpandit.movieapi.dto.request.MovieRequest;
import pt.xpandit.movieapi.dto.response.MovieResponse;
import pt.xpandit.movieapi.exception.MovieNotFoundException;
import pt.xpandit.movieapi.repository.MovieRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing movie business operations.
 */
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    /**
     * Retrieves all movies from the database.
     *
     * @return List of MovieResponse objects containing all movies
     */
    public List<MovieResponse> findAll(){
        return movieRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Creates a new movie in the database.
     *
     * @param movieRequest DTO containing the movie information to be created
     * @return The created Movie entity
     */
    @Transactional
    public Movie create(MovieRequest movieRequest) {
        var movie = new Movie();
        BeanUtils.copyProperties(movieRequest, movie);
        return movieRepository.save(movie);
    }

    /**
     * Finds a movie by ID.
     *
     * @param id The unique identifier of the movie
     * @return MovieResponse object containing the movie information
     * @throws MovieNotFoundException if no movie is found with the given ID
     */
    public MovieResponse findById(Long id) {
        return movieRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
    }

    /**
     * Finds all movies with launch dates within the specified date range.
     *
     * @param startDate The start date of the range
     * @param endDate The end date of the range
     * @return List of MovieResponse within the date range
     */
    public List<MovieResponse> findByLaunchDateRange(LocalDate startDate, LocalDate endDate) {
        return movieRepository.findByLaunchDateBetween(startDate, endDate).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Updates an existing movie's information.
     *
     * @param id The unique identifier of the movie to update
     * @param movieRequest DTO containing the updated movie information
     * @return The updated Movie entity
     * @throws MovieNotFoundException if no movie is found with the given ID
     */
    @Transactional
    public Movie update(Long id, MovieRequest movieRequest) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        BeanUtils.copyProperties(movieRequest, movie);
        return movieRepository.save(movie);
    }

    /**
     * Deletes a movie from the database.
     *
     * @param id The unique identifier of the movie to delete
     * @throws MovieNotFoundException if no movie is found with the given ID
     */
    @Transactional
    public void delete(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movieRepository.delete(movie);
    }

    /**
     * Converts a Movie entity to a MovieResponse DTO.
     *
     * @param movie The Movie entity to convert
     * @return MovieResponse containing the movie information
     */
    private MovieResponse toResponse(Movie movie) {
        return MovieResponse.builder()
                .title(movie.getTitle())
                .launchDate(movie.getLaunchDate())
                .rank(movie.getRank())
                .revenue(movie.getRevenue())
                .build();
    }

}
