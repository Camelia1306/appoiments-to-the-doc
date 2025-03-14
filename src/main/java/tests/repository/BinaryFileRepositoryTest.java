package main.java.tests.repository;

import domain.Pacient;
import domain.Programare;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BinaryFileRepositoryTest {


    private final String tempFile = "tempRepository.bin";
    private BinaryFileRepository<Programare> repository;

    @BeforeEach
    public void setUp() {
        repository = new BinaryFileRepository<>(tempFile);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(tempFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAddAndRetrieve() throws Exception {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Programare programare = new Programare(10, pacient, LocalDateTime.of(2024, 11, 24, 10, 30), "Consultație generală");

        repository.add(programare);
        Programare retrieved = repository.get(10);

        assertEquals(programare, retrieved);
    }

    @Test
    public void testDelete() throws Exception {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Programare programare = new Programare(10, pacient, LocalDateTime.of(2024, 11, 24, 10, 30), "Consultație generală");

        repository.add(programare);
        repository.delete(10);

        assertThrows(Exception.class, () -> repository.get(10));
        assertEquals(0, repository.size());
    }

    @Test
    public void testUpdate() throws Exception {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Programare programare = new Programare(10, pacient, LocalDateTime.of(2024, 11, 24, 10, 30), "Consultație generală");

        repository.add(programare);

        Programare updatedProgramare = new Programare(10, pacient, LocalDateTime.of(2024, 12, 1, 14, 0), "Control periodic");
        repository.update(updatedProgramare);

        Programare retrieved = repository.get(10);
        assertEquals("Control periodic", retrieved.getScop());
    }

    @Test
    public void testGetAll() throws Exception {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Programare programare1 = new Programare(10, pacient, LocalDateTime.of(2024, 11, 24, 10, 30), "Consultație generală");
        Programare programare2 = new Programare(20, pacient, LocalDateTime.of(2024, 12, 1, 14, 0), "Control periodic");

        repository.add(programare1);
        repository.add(programare2);

        assertEquals(2, repository.getAll().size());
    }
}