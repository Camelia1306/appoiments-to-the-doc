package repository;

import domain.Entitate;

import java.util.Collection;

public interface Repository<T extends Entitate> {
    void add(T entity) throws RepositoryExceptions;
    T get(int id) throws RepositoryExceptions;
    void update(T entity) throws RepositoryExceptions;
    void delete(int id) throws RepositoryExceptions;
    Collection<T> getAll();
    int size();
}
