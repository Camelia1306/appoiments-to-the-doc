package main.java.tests.service;

import domain.IdGenerator;
import domain.Pacient;
import org.junit.jupiter.api.Test;
import repository.MemoryRepository;

import static org.junit.jupiter.api.Assertions.*;

class PacientServiceTest {

    @Test
    void addPacient() {
        MemoryRepository memoryRepository = new MemoryRepository();
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        try {
            pacientService.addPacient("n", "p", 21);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void updateAttribute() {
        MemoryRepository memoryRepository = new MemoryRepository();
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        try {
            pacientService.add(pacient);
            pacientService.updateAttribute(101, "varsta", 23);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deletePacient() {
        MemoryRepository memoryRepository = new MemoryRepository();
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        try {
            pacientService.add(pacient);
            pacientService.delete(101);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void getAll() {
        MemoryRepository memoryRepository = new MemoryRepository();
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        try {
            assertEquals(0, pacientService.size());
            pacientService.add(pacient);
            assertEquals(1, pacientService.size());
            pacientService.delete(101);
            assertEquals(0, pacientService.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void get() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        try {
            pacientService.add(pacient);
            assertEquals(101, pacientService.get(101).getId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void update() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        Pacient pacient2 = new Pacient(102, "n2", "p2", 22);
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        try {
            pacientService.add(pacient);
            pacientService.update(pacient2);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void size(){
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(101, "n2", "p2", 22);
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        try {
            assertEquals(0, pacientService.size());
            pacientService.add(pacient);
            assertEquals(1, pacientService.size());
            pacientService.delete(101);
            assertEquals(0, pacientService.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}