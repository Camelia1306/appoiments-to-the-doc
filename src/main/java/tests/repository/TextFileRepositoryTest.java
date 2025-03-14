package main.java.tests.repository;

import domain.Pacient;
import domain.PacientConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextFileRepositoryTest {

    private final String tempFile = "C:\\Users\\w\\IdeaProjects\\a3-Camelia1306\\src\\tempPacienti.txt";
    private TextFileRepository<Pacient> repository;

    @BeforeEach
    public void setUp() {
        repository = new TextFileRepository<>(tempFile, new PacientConverter());
    }

    @AfterEach
    public void tearDown() {
        File file = new File(tempFile);
        if (file.exists()) {
            file.delete(); // Șterge fișierul temporar după fiecare test
        }
    }

    @Test
    public void testAddAndRetrieve() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        try {
        repository.add(pacient);}
        catch (Exception e) {
            e.printStackTrace();
        }

        Collection<Pacient> allPacients = repository.getAll();
        assertEquals(1, allPacients.size());
        assertEquals(pacient, allPacients.iterator().next());
    }
}