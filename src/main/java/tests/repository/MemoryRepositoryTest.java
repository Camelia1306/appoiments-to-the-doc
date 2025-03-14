package main.java.tests.repository;

import domain.Pacient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryRepositoryTest {

    @Test
    void add() {
        MemoryRepository<Pacient> memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(100, "n", "p", 20);
        assertEquals(0, memoryRepository.size());
        try{
            memoryRepository.add(pacient);}
        catch(Exception e){
            fail();
        }
        assertEquals(1, memoryRepository.size());
    }

    @Test
    void get() {
        MemoryRepository<Pacient> memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(100, "n", "p", 20);
        try{ memoryRepository.add(pacient);}
        catch(Exception e){
            fail();
        }

       try{ assertEquals(true,pacient.equals(memoryRepository.get(100))); }
       catch(Exception e){
           fail();
       }
    }

    @Test
    void update() {
        MemoryRepository<Pacient> memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(100, "n", "p", 20);
        try {
            memoryRepository.add(pacient);
        } catch (Exception e) {
            fail();
        }
        try {
            memoryRepository.update(pacient);
        } catch (RepositoryExceptions e) {
            fail();
        }
    }

        @Test
    void delete() {
            MemoryRepository<Pacient> memoryRepository = new MemoryRepository();
            Pacient pacient = new Pacient(100, "n", "p", 20);
            assertEquals(0, memoryRepository.size());
            try {
                memoryRepository.add(pacient);
            } catch (Exception e) {
                fail();
            }
            assertEquals(1, memoryRepository.size());
            try {
                memoryRepository.delete(100);
            } catch (RepositoryExceptions e) {
                fail();
            }
            assertEquals(0, memoryRepository.size());
        }

    @Test
    void getAll() {
        MemoryRepository<Pacient> memoryRepository = new MemoryRepository();
        Pacient pacient = new Pacient(100, "n", "p", 20);
        try {
            memoryRepository.add(pacient);
        } catch (Exception e) {
            fail();
        }
        assertEquals(1, memoryRepository.size());
        assertTrue(memoryRepository.getAll().contains(pacient));
    }
}