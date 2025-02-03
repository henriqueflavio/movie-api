package pt.xpandit.movieapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.xpandit.movieapi.domain.Movie;
import pt.xpandit.movieapi.dto.request.MovieRequest;
import pt.xpandit.movieapi.dto.response.MovieResponse;
import pt.xpandit.movieapi.service.MovieService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieServiceMock;

    @Test
    void shouldCreateMovie() throws Exception {
        MovieRequest request = createMovieRequest();
        Movie expectedMovie = createMovie();

        when(movieServiceMock.create(any(MovieRequest.class))).thenReturn(expectedMovie);

        mockMvc.perform(post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedMovie)));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidRequest() throws Exception {
        MovieRequest invalidRequest = new MovieRequest(
                "",
                LocalDate.of(1999, 3, 31),
                BigDecimal.valueOf(11),
                BigDecimal.valueOf(-1)
        );

        mockMvc.perform(post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetMovieById() throws Exception {
        MovieResponse expectedMovieResponse = createMovieResponse();

        when(movieServiceMock.findById(1L)).thenReturn(expectedMovieResponse);

        mockMvc.perform(get("/api/v1/movies/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedMovieResponse)));
    }

    @Test
    void shouldGetAllMovies() throws Exception {
        List<MovieResponse> expectedMovies = List.of(createMovieResponse());
        when(movieServiceMock.findAll()).thenReturn(expectedMovies);

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedMovies)));
    }

    @Test
    void shouldSearchMoviesByDateRange() throws Exception {
        List<MovieResponse> expectedMovies = List.of(createMovieResponse());
        when(movieServiceMock.findByLaunchDateRange(any(), any())).thenReturn(expectedMovies);

        mockMvc.perform(get("/api/v1/movies/filter")
                        .param("startDate", "1999-01-01")
                        .param("endDate", "1999-12-31"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedMovies)));
    }

    @Test
    void shouldUpdateMovie() throws Exception {
        MovieRequest request = createMovieRequest();

        when(movieServiceMock.update(eq(1L), any(MovieRequest.class))).thenReturn(createMovie());

        mockMvc.perform(put("/api/v1/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteMovie() throws Exception {
        doNothing().when(movieServiceMock).delete(1L);

        mockMvc.perform(delete("/api/v1/movies/1"))
                .andExpect(status().isOk());
    }

    private MovieResponse createMovieResponse() {
        return MovieResponse.builder()
                .title("The Matrix")
                .launchDate(LocalDate.of(1999, 3, 31))
                .rank(BigDecimal.valueOf(8.7))
                .revenue(BigDecimal.valueOf(463517383))
                .build();
    }

    private Movie createMovie() {
       return Movie.builder()
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