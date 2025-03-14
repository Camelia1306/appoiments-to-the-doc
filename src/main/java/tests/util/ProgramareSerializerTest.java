package main.java.tests.util;

import domain.Pacient;
import domain.Programare;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProgramareSerializerTest {


    private final String tempFile = "C:\\Users\\w\\IdeaProjects\\a3-Camelia1306\\src\\tempProgramari.bin";
    private ProgramareSerializer serializer;

    @BeforeEach
    public void setUp() {
        serializer = new ProgramareSerializer();
    }

    @AfterEach
    public void tearDown() {
        File file = new File(tempFile);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSerializeAndDeserialize() throws Exception {
        List<Programare> programari = new ArrayList<>();
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        programari.add(new Programare(10, pacient, LocalDateTime.of(2024, 11, 24, 10, 30), "Consultație generală"));
        programari.add(new Programare(20, pacient, LocalDateTime.of(2024, 12, 1, 14, 0), "Control periodic"));

        serializer.serialize(programari, tempFile);

        List<Programare> deserializedProgramari = serializer.deserialize(tempFile);
        assertEquals(programari, deserializedProgramari); // Verifică egalitatea colecțiilor
    }

    @Test
    public void testDeserializeNonexistentFile() {
        List<Programare> programari = serializer.deserialize("nonexistent.bin");
        assertNotNull(programari);
        assertTrue(programari.isEmpty());
    }
}