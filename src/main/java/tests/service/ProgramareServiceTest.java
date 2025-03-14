package main.java.tests.service;

import domain.IdGenerator;
import domain.Pacient;
import domain.Programare;
import org.junit.jupiter.api.Test;
import repository.MemoryRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProgramareServiceTest {

    @Test
    void add() {
        MemoryRepository memoryRepository = new MemoryRepository();
        ProgramareService programareService = new ProgramareService(memoryRepository, new IdGenerator("IDTEST"));
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        Programare programare = new Programare(102, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "Control");
        try {
            assertEquals(0, programareService.size());
            programareService.add(programare);
            assertEquals(1, programareService.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void get() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        ProgramareService programareService = new ProgramareService(memoryRepository, new IdGenerator("IDTEST"));
        Programare programare = new Programare(102, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "Control");
        try {
            programareService.add(programare);
            assertEquals(programare, programareService.get(programare.getId()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void update() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        ProgramareService programareService = new ProgramareService(memoryRepository, new IdGenerator("IDTEST"));
        Programare programare = new Programare(102, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "Control");
        try {
            programareService.add(programare);
            programareService.update(programare);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        ProgramareService programareService = new ProgramareService(memoryRepository, new IdGenerator("IDTEST"));
        Programare programare = new Programare(102, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "Control");
        try {
            assertEquals(0, programareService.size());
            programareService.add(programare);
            assertEquals(1, programareService.size());
            programareService.delete(programare.getId());
            assertEquals(0, programareService.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addProgramare() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        ProgramareService programareService = new ProgramareService(memoryRepository, new IdGenerator("IDTEST"));
        try {
            assertEquals(0, programareService.size());
            programareService.addProgramare(pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "Control");
            assertEquals(1, programareService.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAll() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        ProgramareService programareService = new ProgramareService(memoryRepository, new IdGenerator("IDTEST"));
        Programare programare = new Programare(102, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "Control");
        try {
            assertEquals(0, programareService.size());
            programareService.add(programare);
            assertEquals(1, programareService.size());
            assertEquals(programare, programareService.get(programare.getId()));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    void updateAttribute() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        Pacient pacient1 = new Pacient(104, "n2", "p2", 22);
        PacientService pacientService =  new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        try {
            pacientService.add(pacient);
            pacientService.add(pacient1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Programare programare = new Programare(102, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "Control");
        ProgramareService programreservice = new ProgramareService(memoryRepository, new IdGenerator("IDTEST"));
        try {
            programreservice.add(programare);
            programreservice.updateAttribute(102, "scop", "aaa");
            programreservice.updateAttribute(102, "pacient", 104);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void size() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        ProgramareService programareService = new ProgramareService(memoryRepository, new IdGenerator("IDTEST"));
        Programare programare = new Programare(102, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "Control");
        try {
            assertEquals(0, programareService.size());
            programareService.add(programare);
            assertEquals(1, programareService.size());
            programareService.delete(programare.getId());
            assertEquals(0, programareService.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}