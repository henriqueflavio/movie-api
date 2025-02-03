package pt.xpandit.movieapi.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException() {
        super("Movie not found");
    }

    public MovieNotFoundException(String message) {
        super(message);
    }
}
