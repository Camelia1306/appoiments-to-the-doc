package repository;

import domain.Entitate;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BinaryFileRepository<T extends Entitate> implements Repository<T> {
    private final String filename;
    private Map<Integer, T> entities = new HashMap<>();

    public BinaryFileRepository(String filename) {
        this.filename = filename;
        loadFromFile();
    }

    private void loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            entities = (Map<Integer, T>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Fișierul binar nu a fost găsit. Creăm un repository gol.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nu s-a putut citi fișierul binar: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(entities);
        } catch (IOException e) {
            System.out.println("Nu s-a putut salva în fișierul binar: " + e.getMessage());
        }
    }

    @Override
    public void add(T entity) throws RepositoryExceptions {
        if (entities.containsKey(entity.getId())) {
            throw new RepositoryExceptions("ID-ul există deja!");
        }
        entities.put(entity.getId(), entity);
        saveToFile();
    }

    @Override
    public T get(int id) throws RepositoryExceptions {
        if (!entities.containsKey(id)) {
            throw new RepositoryExceptions("Entitatea nu a fost găsită!");
        }
        return entities.get(id);
    }

    @Override
    public void update(T entity) throws RepositoryExceptions {
        if (!entities.containsKey(entity.getId())) {
            throw new RepositoryExceptions("Entitatea nu a fost găsită pentru actualizare!");
        }
        entities.put(entity.getId(), entity);
        saveToFile();
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        if (!entities.containsKey(id)) {
            throw new RepositoryExceptions("Entitatea nu a fost găsită pentru ștergere!");
        }
        entities.remove(id);
        saveToFile();
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
