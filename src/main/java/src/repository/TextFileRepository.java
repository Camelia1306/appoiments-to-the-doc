package repository;

import domain.Entitate;
import domain.EntitateConverter;
import repository.RepositoryExceptions;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TextFileRepository<T extends Entitate> implements repository.Repository<T> {
    private final String filename;
    private final EntitateConverter<T> converter;
    private Map<Integer, T> entities = new HashMap<>();

    public TextFileRepository(String filename, EntitateConverter<T> converter) {
        this.filename = filename;
        this.converter = converter;
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T entity = converter.fromString(line);
                entities.put(entity.getId(), entity);
            }
        } catch (IOException e) {
            System.out.println("Nu s-a putut citi fișierul text: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (T entity : entities.values()) {
                writer.write(converter.toString(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Nu s-a putut salva în fișierul text: " + e.getMessage());
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
