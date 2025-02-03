package pt.xpandit.movieapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.xpandit.movieapi.domain.Movie;
import pt.xpandit.movieapi.dto.request.MovieRequest;
import pt.xpandit.movieapi.dto.response.MovieResponse;
import pt.xpandit.movieapi.exception.MovieNotFoundException;
import pt.xpandit.movieapi.repository.MovieRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepositoryMock;

    @InjectMocks
    private MovieService movieService;

    @Test
    void shouldCreateMovie() {
        MovieRequest request = createMovieRequest();
        Movie expectedMovie = createMovie();

        when(movieRepositoryMock.save(any(Movie.class))).thenReturn(expectedMovie);
        Movie response = movieService.create(request);

        assertThat(response).usingRecursiveComparison().isEqualTo(expectedMovie);
        verify(movieRepositoryMock).save(any(Movie.class));
    }

    @Test
    void shouldFindMovieById() {
        Movie movie = createMovie();
        MovieResponse expectedResponse = createMovieResponse();

        when(movieRepositoryMock.findById(1L)).thenReturn(Optional.of(movie));
        MovieResponse response = movieService.findById(1L);

        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    void shouldThrowExceptionWhenMovieNotFound() {
        when(movieRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> movieService.findById(1L))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessage("Movie not found with id: 1");
    }

    @Test
    void shouldFindAllMovies() {
        Movie movie = createMovie();
        List<Movie> movies = List.of(movie);
        List<MovieResponse> expectedMovies = List.of(createMovieResponse());

        when(movieRepositoryMock.findAll()).thenReturn(movies);
        List<MovieResponse> responses = movieService.findAll();

        assertThat(responses).usingRecursiveComparison().isEqualTo(expectedMovies);
    }

    @Test
    void shouldFindMoviesByDateRange() {
        Movie movie = createMovie();
        LocalDate startDate = LocalDate.of(1999, 1, 1);
        LocalDate endDate = LocalDate.of(1999, 12, 31);
        List<Movie> movies = List.of(movie);
        List<MovieResponse> expectedMovies = List.of(createMovieResponse());

        when(movieRepositoryMock.findByLaunchDateBetween(startDate, endDate)).thenReturn(movies);
        List<MovieResponse> responses = movieService.findByLaunchDateRange(startDate, endDate);

        assertThat(responses).usingRecursiveComparison().isEqualTo(expectedMovies);
    }

    private Movie createMovie() {
        return Movie.builder()
                .id(1L)
                .title("The Matrix")
                .launchDate(LocalDate.of(1999, 3, 31))
                .rank(BigDecimal.valueOf(8.7))
                .revenue(BigDecimal.valueOf(463517383))
                .build();
    }

    private MovieResponse createMovieResponse() {
        return MovieResponse.builder()
                .title("The Matrix")
                .launchDate(LocalDate.of(1999, 3, 31))
                .rank(BigDecimal.valueOf(8.7))
                .revenue(BigDecimal.valueOf(463517383))
                .build();
    }

    private MovieRequest createMovieRequest() {
        return new MovieRequest(
                "The Matrix",
                LocalDate.of(1999, 3, 31),
                BigDecimal.valueOf(8.7),
                BigDecimal.valueOf(463517383)
        );
    }
}