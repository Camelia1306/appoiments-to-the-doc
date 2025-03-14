package main.java.tests.service;

import domain.IdGenerator;
import domain.Pacient;
import org.junit.jupiter.api.Test;
import repository.MemoryRepository;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    @Test
    void add() {
        MemoryRepository memoryRepository = new MemoryRepository();
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        Pacient pacient = new Pacient(100, "n", "p", 20);
        assertEquals(0, pacientService.size());
        try {
            pacientService.add(pacient);
            assertEquals(1, pacientService.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void get() {
        MemoryRepository memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(100, "n", "p", 20);
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        try {
            pacientService.add(pacient);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(pacient, pacientService.get(pacient.getId()));
    }

    @Test
    void update() {
        MemoryRepository memoryRepository = new MemoryRepository();
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        Pacient pacient = new Pacient(100, "n", "p", 20);
        assertEquals(0, pacientService.size());
        try {
            pacientService.add(pacient);
            pacientService.updateAttribute(100,"varsta", 21);
            pacientService.update(pacient);
            assertEquals(21, pacient.getVarsta());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        MemoryRepository memoryRepository = new MemoryRepository();
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        Pacient pacient = new Pacient(100, "n", "p", 20);
        assertEquals(0, pacientService.size());
        try {
            pacientService.add(pacient);
            assertEquals(1, pacientService.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            pacientService.delete(pacient.getId());
            assertEquals(0, pacientService.size());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAll() {
        MemoryRepository memoryRepository = new MemoryRepository();
        PacientService pacientService = new PacientService(memoryRepository, new IdGenerator("IDTEST"));
        Pacient pacient = new Pacient(100, "n", "p", 20);
        assertEquals(0, pacientService.size());
        try {
            pacientService.add(pacient);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(pacientService.getAll().containsAll(pacientService.getAll()));
    }
}