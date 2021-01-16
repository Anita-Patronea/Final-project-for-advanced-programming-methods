package repository;

public class RepositoryPersistenceException extends RuntimeException{
    public RepositoryPersistenceException(){}
    public RepositoryPersistenceException(String message){ super(message); }
    public RepositoryPersistenceException(Exception exception){
        super(exception);
    }
}
