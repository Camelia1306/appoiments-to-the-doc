package repository;

import domain.Entitate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MemoryRepository <T extends Entitate> implements Repository<T> {
    Map<Integer, T> entities = new HashMap<>();

    @Override
    public void add(T entity) throws RepositoryExceptions {
        if (entities.containsKey(entity.getId())) {
            throw new RepositoryExceptions("ID-ul există deja!");
        }
        entities.put(entity.getId(), entity);
    }


    @Override
    public T get(int id) throws RepositoryExceptions {
        if (!entities.containsKey(id)) {
            throw new RepositoryExceptions("Entitatea nu a fost gasita!");
        }
        return entities.get(id);
    }

    @Override
    public void update(T entity) throws RepositoryExceptions {
        if (!entities.containsKey(entity.getId())) {
            throw new RepositoryExceptions("Entitatea nu a fost gasita pentru actualizare!");
        }
        entities.put(entity.getId(), entity);
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        if (!entities.containsKey(id)) {
            throw new RepositoryExceptions("Entitatea nu a fost gasita pentru ștergere!");
        }
        entities.remove(id);
    }

    @Override
    public Collection<T> getAll() {
        return entities.values();
    }

    @Override
    public int size() {
        return entities.size();
    }
}
