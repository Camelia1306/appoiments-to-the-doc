package repository;

import domain.Entitate;

import java.io.IOException;

public interface FileRepository<T extends Entitate> extends Repository<T> {
    void saveFile() throws IOException, RepositoryExceptions;
    void loadFile() throws IOException, RepositoryExceptions;
}

