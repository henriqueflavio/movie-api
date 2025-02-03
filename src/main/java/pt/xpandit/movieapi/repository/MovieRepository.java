package pt.xpandit.movieapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.xpandit.movieapi.domain.Movie;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Movie entities.
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Finds all movies with launch dates within a specified date range.
     * The search is inclusive of both start and end dates.
     *
     * @param startDate The beginning of the date range
     * @param endDate The end of the date range
     * @return List of movies with launch dates within the specified range
     *         Returns an empty list if no movies are found
     */
    List<Movie> findByLaunchDateBetween(LocalDate startDate, LocalDate endDate);

}
