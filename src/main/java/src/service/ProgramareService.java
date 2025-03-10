package service;

import domain.IdGenerator;
import domain.Pacient;
import domain.Programare;
import repository.Repository;
import repository.RepositoryExceptions;
import validation.Validator;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

public class ProgramareService implements Service<Programare> {
    private final Repository<Programare> programareRepository;
    private final IdGenerator idGenerator;

    public ProgramareService(Repository<Programare> programareRepository, IdGenerator idGenerator) {
        this.programareRepository = programareRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public void add(Programare programare) {
        try{
            Collection<Programare> programariExistente = programareRepository.getAll();
            Validator.validateProgramare(programare, programariExistente);
            programareRepository.add(programare);}
        catch (RepositoryExceptions re) {
            System.out.println("id dublicat!");
        }
    }

    @Override
    public Programare get(int id) {
        try {
            return programareRepository.get(id);}
        catch (RepositoryExceptions re) {
            System.out.println("id nu exista!");
        }
        return null;
    }

    @Override
    public void update(Programare entity) {

    }

    @Override
    public void delete(int id) {
        try{
            programareRepository.delete(id);}
        catch(RepositoryExceptions e){
            System.out.println("nu s-a sters programarea!");
        }
    }


    public void addProgramare(Pacient pacient, LocalDateTime data, String scop)  {
        try{
            int id = idGenerator.generateId();
            Programare programare = new Programare(id, pacient, data, scop);
            Collection<Programare> programariExistente = programareRepository.getAll();
            Validator.validateProgramare(programare, programariExistente);
            programareRepository.add(programare); }
        catch(RepositoryExceptions e){
            System.out.println("nu s-a facut programarea!");
        }
    }


    public Collection<Programare> getAll() {
        return programareRepository.getAll();
    }
    private Programare findEntityById(int id) {
        try { Programare programare = programareRepository.get(id);
            if (programare == null) {
                throw new IllegalArgumentException("Pacientul cu ID-ul " + id + " nu a fost gasit!");
            }
            return programare;
        }
        catch (RepositoryExceptions re) {
            System.out.println("id-ul nu exista!");
        }
        return null;
    }

    public void updateAttribute(int id, String attributeName, Object newValue) {
        Programare programare = findEntityById(id);
        if (programare == null) {
            System.out.println("Programarea cu id-ul " + " nu exista.");
            return;
        }
        try {
            Field field = Programare.class.getDeclaredField(attributeName);
            field.setAccessible(true);
            field.set(programare, newValue);
            programareRepository.update(programare);
        } catch (RepositoryExceptions re) {
            System.out.println("Eroare la actualizarea programarii în repository: " + re.getMessage());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Eroare la actualizarea atributului: " + e.getMessage());
        }
    }
    @Override
    public int size()
    {
        return programareRepository.size();
    }


    public Map<Pacient, Long> numarProgramariPerPacient(List<Programare> programari) {
        return programari.stream()
                .collect(Collectors.groupingBy(
                        Programare::getPacient,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Pacient, Long>comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // pentru a păstra ordinea descrescătoare
                ));
    }

    public Map<Month, Long> numarProgramariPerLuna(List<Programare> programari) {
        return programari.stream()
                .collect(Collectors.groupingBy(
                        programare -> programare.getData().getMonth(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Month, Long>comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // pentru ordinea descrescătoare
                ));
    }


    public Map<Pacient, Long> zileDeLaUltimaProgramare(List<Programare> programari) {
        LocalDate azi = LocalDate.now();
        return programari.stream()
                .collect(Collectors.groupingBy(
                        Programare::getPacient,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(Programare::getData)),
                                opt -> opt.map(p -> ChronoUnit.DAYS.between(p.getData().toLocalDate(), azi)).orElse(0L)
                        )
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Pacient, Long>comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }


    public Map.Entry<Month, Long> celeMaiAglomerateLuni(List<Programare> programari) {
        return programari.stream()
                .collect(Collectors.groupingBy(
                        programare -> programare.getData().getMonth(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()) // Get the max entry (most appointments)
                .orElse(null); // Return null if there are no appointments
    }


}

