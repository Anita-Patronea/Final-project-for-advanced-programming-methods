package repository;

public class RepositoryException extends RuntimeException{
    public RepositoryException(){}
    public RepositoryException(String message){ super(message); }
    public RepositoryException(Exception exception){
        super(exception);
    }
}
