package eu.senla.taxibooking.exception;

public class BookingNotFoundException extends RepositoryException {

    public BookingNotFoundException(String message) {
        super(message);
    }
}
