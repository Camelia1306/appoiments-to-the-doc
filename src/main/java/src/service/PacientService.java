package service;

import domain.IdGenerator;
import domain.Pacient;
import repository.Repository;
import repository.RepositoryExceptions;
import validation.Validator;

import java.lang.reflect.Field;
import java.util.Collection;

public class PacientService implements Service<Pacient> {
    private final Repository<Pacient> pacientRepository;
    private final IdGenerator idGenerator;

    public PacientService(Repository<Pacient> pacientRepository, IdGenerator idGenerator) {
        this.pacientRepository =     pacientRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public void add(Pacient pacient) {
        try{
            Validator.validatePacient(pacient);
            pacientRepository.add(pacient);}
        catch (RepositoryExceptions re) {
            System.out.println("id dublicat!");
        }
    }

    public void addPacient(String nume, String prenume, int varsta) {
        int id = idGenerator.generateId();
        Pacient pacient = new Pacient(id, nume, prenume, varsta);
        try{ Validator.validatePacient(pacient);
            pacientRepository.add(pacient);}
        catch (RepositoryExceptions re) {
            System.out.println("pacientul nu a fost adaugat. datele nu au fost corecte!");
        }
    }

    @Override
    public Pacient get(int id) {
        try {
            return pacientRepository.get(id);}
        catch (RepositoryExceptions re) {
            System.out.println("id nu exista!");
        }
        return null;
    }

    @Override
    public void update(Pacient pacient) {
        try{
            Validator.validatePacient(pacient);
            pacientRepository.update(pacient);
        }
        catch (RepositoryExceptions re) {
            System.out.println("didn't update!");
        }
    }

    private Pacient findEntityById(int id) {
        try { Pacient pacient = pacientRepository.get(id);
            if (pacient == null) {
                throw new IllegalArgumentException("Pacientul cu ID-ul " + id + " nu a fost găsit!");
            }
            return pacient;
        }
        catch (RepositoryExceptions re) {
            System.out.println("id nu exista!");
        }
        return null;
    }

    public void updateAttribute(int id, String attributeName, Object newValue) {
        Pacient pacient = findEntityById(id);
        try {
            Field field = Pacient.class.getDeclaredField(attributeName);
            field.setAccessible(true);
            field.set(pacient, newValue);
            try {
                Validator.validatePacient(pacient);
                pacientRepository.update(pacient); }
            catch (RepositoryExceptions re ){
                System.out.println("problems with update!");}
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Eroare la actualizarea atributului: " + attributeName, e);
        }
    }

    @Override
    public void delete(int id) {
        try{ pacientRepository.delete(id);}
        catch (RepositoryExceptions re) {
            System.out.println("didn't delete!");
        }
    }

    @Override
    public Collection<Pacient> getAll() {
        return pacientRepository.getAll();
    }

    @Override
    public int size(){
        return pacientRepository.size();
    }
}
