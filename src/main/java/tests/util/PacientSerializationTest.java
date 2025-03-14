package main.java.tests.util;

import domain.Pacient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacientSerializationTest {

    private final String tempFile = "tempPacient.bin";
    private PacientSerialization serializer;

    @BeforeEach
    public void setUp() {
        serializer = new PacientSerialization();
    }

    @AfterEach
    public void tearDown() {
        File file = new File(tempFile);
        if (file.exists()) {
            file.delete(); // Șterge fișierul temporar după fiecare test
        }
    }

    @Test
    public void testSerializeAndDeserialize() {
        List<Pacient> originalPacients = new ArrayList<>();
        originalPacients.add(new Pacient(1, "Ion", "Popescu", 30));
        originalPacients.add(new Pacient(2, "Maria", "Ionescu", 25));

        // Serialize
        serializer.serialize(originalPacients, tempFile);

        // Deserialize
        List<Pacient> deserializedPacients = serializer.deserialize(tempFile);

        // Compară listele
        assertEquals(originalPacients.size(), deserializedPacients.size());
        assertEquals(originalPacients, deserializedPacients); // `equals` trebuie să fie implementat în clasa `Pacient`
    }
}