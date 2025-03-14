package main.java.tests.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacientConverterTest {

    @Test
    public void testToString() {
        Pacient pacient = new Pacient(1, "Ion", "Popescu", 30);
        PacientConverter converter = new PacientConverter();

        String result = converter.toString(pacient);
        assertEquals("1,Ion,Popescu,30", result); // Presupunând că formatul este acesta
    }

    @Test
    public void testFromString() {
        String data = "1,Ion,Popescu,30";
        PacientConverter converter = new PacientConverter();

        Pacient pacient = converter.fromString(data);
        assertEquals(1, pacient.getId());
        assertEquals("Ion", pacient.getNume());
        assertEquals("Popescu", pacient.getPrenume());
        assertEquals(30, pacient.getVarsta());
    }
}