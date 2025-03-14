package main.java.tests.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProgramareConverterTest {

    @Test
    public void testToString() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        Programare programare = new Programare(10, pacient, LocalDateTime.of(2024, 11, 24, 10, 30), "Consultație generală");
        ProgramareConverter converter = new ProgramareConverter();

        String result = converter.toString(programare);
        assertEquals("10;1,Ion,Popescu,30;2024-11-24T10:30:00;Consultație generală", result);
    }

    @Test
    public void testFromString() {
        String data = "10;1,Ion,Popescu,30;2024-11-24T10:30;Consultație generală";
        ProgramareConverter converter = new ProgramareConverter();

        Programare programare = converter.fromString(data);
        assertEquals(10, programare.getId());
        assertEquals("Ion", programare.getPacient().getNume());
        assertEquals("Popescu", programare.getPacient().getPrenume());
        assertEquals(30, programare.getPacient().getVarsta());
        assertEquals(LocalDateTime.of(2024, 11, 24, 10, 30), programare.getData());
        assertEquals("Consultație generală", programare.getScop());
    }

    @Test
    public void testFromStringInvalidFormat() {
        String invalidData = "10;1,Ion,Popescu;Consultație generală"; // Format incorect
        ProgramareConverter converter = new ProgramareConverter();

        assertThrows(IllegalArgumentException.class, () -> converter.fromString(invalidData));
    }
}