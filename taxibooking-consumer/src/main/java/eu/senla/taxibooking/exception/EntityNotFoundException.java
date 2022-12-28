package eu.senla.taxibooking.exception;

public class EntityNotFoundException extends RepositoryException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
