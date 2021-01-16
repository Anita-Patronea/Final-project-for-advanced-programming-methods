package repository;

import java.util.Collection;

public interface Repository <T, Tid>{
    Tid add (T elem);
    void delete (Tid id);
    void update (T elem, Tid id);
    T findById (Tid id);
    Iterable<T> findAll();
    Collection<T> getAll();
}
