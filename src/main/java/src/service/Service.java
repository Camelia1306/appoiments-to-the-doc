package service;

import domain.Entitate;
import repository.RepositoryExceptions;

import java.util.Collection;

public interface Service<T extends Entitate> {
    void add(T entity) throws RepositoryExceptions;
    T get(int id);
    void update(T entity);
    void delete(int id);
    Collection<T> getAll();
    int size();
}
