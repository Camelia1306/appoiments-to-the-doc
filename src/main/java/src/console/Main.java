package console;

import domain.*;
import repository.*;
import service.PacientService;
import service.ProgramareService;
import util.Configuration;
import util.PacientSerialization;
import util.ProgramareSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        Configuration config = new Configuration("src//main//java//src//settings.properties");

        String repositoryType = config.getProperty("Repository");
        Repository<Pacient> pacientRepository;
        Repository<Programare> programareRepository;

        switch (repositoryType) {
            case "memory":
                pacientRepository = new MemoryRepository<>();
                programareRepository = new MemoryRepository<>();
                break;
            case "text":
                pacientRepository = new TextFileRepository<>(config.getProperty("Patients"), new PacientConverter());
                programareRepository = new TextFileRepository<>(config.getProperty("Appointments"), new ProgramareConverter());
                break;
            case "binary":
                pacientRepository = new BinaryFileRepository<>(config.getProperty("Patients"));
                programareRepository = new BinaryFileRepository<>(config.getProperty("Appointments"));
                break;
            case "sql":
                pacientRepository = new SQLRepository();
                programareRepository = new SQLRepositoryProgramari();
                break;
            default:
                throw new RuntimeException("Tipul de repository nu este valid!");
        }

        PacientSerialization pacientSerialization = new PacientSerialization();
        List<Pacient> pacients = pacientSerialization.deserialize("C:\\Users\\w\\IdeaProjects\\first_try_of_fx\\src\\main\\java\\src\\ListaPacientiSerializata.bin");
        if (pacients == null) {
            pacients = new ArrayList<>();
        }

        ProgramareSerializer programareSerialization = new ProgramareSerializer();
        List<Programare> programari = programareSerialization.deserialize("C:\\Users\\w\\IdeaProjects\\first_try_of_fx\\src\\main\\java\\src\\ListaProgramariSerializata.bin");
        if (programari == null) {
            programari = new ArrayList<>() {
            };
        }

        IdGenerator idGenerator = new IdGenerator("C:\\Users\\w\\IdeaProjects\\first_try_of_fx\\src\\main\\java\\src\\ID");

        PacientService pacientService = new PacientService(pacientRepository, idGenerator);
        ProgramareService programareService = new ProgramareService(programareRepository, idGenerator);
//            Pacient p1 = new Pacient(idGenerator.generateId(), "n1", "p1", 20);
//        pacientService.add(p1);
//        Pacient p2 = new Pacient(idGenerator.generateId(), "n2", "p2", 20);
//        pacientService.add(p2);
//        Pacient p3 = new Pacient(idGenerator.generateId(), "n3", "p3", 20);
//        pacientService.add(p3);
//        Pacient p4 = new Pacient(idGenerator.generateId(), "n4", "p4", 20);
//        pacientService.add(p4);
//        Pacient p5 = new Pacient(idGenerator.generateId(), "n5", "p5", 20);
//        pacientService.add(p5);

//        programareService.addProgramare(p1, LocalDateTime.of(2025, 10, 30, 10, 0), "Control");
//        programareService.addProgramare(p2,LocalDateTime.of(2025, 11, 1, 11, 0), "Consultație");
//        programareService.addProgramare(p3,LocalDateTime.of(2025, 1, 2, 12, 0), "Tratamente");
//        programareService.addProgramare(p4, LocalDateTime.of(2025, 11, 2, 12, 0), "Tratamente");
//        programareService.addProgramare(p5, LocalDateTime.of(2025, 11, 4, 14, 0), "Control");

        ConsoleApp consoleApp = new ConsoleApp(pacientService, programareService);
        consoleApp.run();

        PacientSerialization serializerPacienti = new PacientSerialization();
        ArrayList<Pacient> pacienti = new ArrayList<> (pacientRepository.getAll());
        serializerPacienti.serialize(pacienti, "C:\\Users\\w\\IdeaProjects\\first_try_of_fx\\src\\main\\java\\src\\ListaPacientiSerializata.bin");

        ProgramareSerializer serializerProgramari = new ProgramareSerializer();
        serializerProgramari.serialize(new ArrayList<>(programareRepository.getAll()), "C:\\Users\\w\\IdeaProjects\\first_try_of_fx\\src\\main\\java\\src\\ListaProgramariSerializata.bin");

    }
}
