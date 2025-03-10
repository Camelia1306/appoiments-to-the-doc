package repository;
import domain.Entitate;

public abstract class AbstractFileRepository<T extends Entitate> extends MemoryRepository<T> {
    protected String fileName;

    public AbstractFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void add(T elem) throws RepositoryExceptions {
        super.add(elem);
        saveFile();
    }

    @Override
    public void delete(int id) throws RepositoryExceptions {
        super.delete(id);
        saveFile();
    }


    protected abstract void saveFile() throws RepositoryExceptions;

    protected abstract void loadFile() throws RepositoryExceptions;

}
