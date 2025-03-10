package repository;

import domain.Entitate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractRepository<T extends Entitate> implements Iterable<T> {

    protected List<T> data = new ArrayList<>();

    public abstract void add(T elem) throws RepositoryExceptions;

    public abstract void delete(T elem) throws RepositoryExceptions;

    public abstract T find(int id);

    public int size() {
        return this.data.size();
    }

    public Collection<T> getAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }
}
