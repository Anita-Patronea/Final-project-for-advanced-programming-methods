package repository;

import domain.Identifiable;

import java.util.*;

public abstract class AbstractRepository <T extends Identifiable<Tid>, Tid> implements Repository<T, Tid>{
    protected Map<Tid, T> map;

    public AbstractRepository(){
        map = new HashMap<>();
    }

    @Override
    public Tid add(T elem){
        if(map.containsKey(elem.getID())) {
            throw new RepositoryException("Element already exists: " + elem);
        }
        else {
            map.put(elem.getID(), elem);
            return elem.getID();
        }
    }

    @Override
    public void delete(Tid id){
        if(map.containsKey(id)) {
            map.remove(id);
        }
        else {
            throw new RepositoryException("Element does not exist: " + id);
        }
    }

    @Override
    public void update(T elem,Tid id){
        if(map.containsKey(id)) {
            elem.setID(id);
            map.put(id, elem);
        }
        else {
            throw new RepositoryException("Element does not exist: " + id);
        }
    }

    @Override
    public T findById( Tid id){
        if(map.containsKey(id)) {
            return map.get(id);
        }
        else {
            throw new RepositoryException("Element does not exist: " + id);
        }
    }

    @Override
    public Iterable<T> findAll(){
        return map.values();
    }

    @Override
    public Collection<T> getAll(){ return map.values(); }
}
