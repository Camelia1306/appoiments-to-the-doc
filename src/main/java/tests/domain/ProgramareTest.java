package main.java.tests.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProgramareTest {

    @Test
    void getPacient() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        Programare programare = new Programare(200, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "aaa");
        assertEquals(pacient, programare.getPacient());
    }

    @Test
    void setPacient() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        Programare programare = new Programare(200, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "aaa");
        assertEquals(LocalDateTime.of(2025, 10, 30, 10, 0), programare.getData());
        programare.setPacient(pacient);
        assertEquals(pacient, programare.getPacient());
    }

    @Test
    void getData() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        Programare programare = new Programare(200, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "aaa");
        assertEquals(LocalDateTime.of(2025, 10, 30, 10, 0), programare.getData());
    }

    @Test
    void setData() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        Programare programare = new Programare(200, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "aaa");
        assertEquals(LocalDateTime.of(2025, 10, 30, 10, 0), programare.getData());
        programare.setData(LocalDateTime.of(2026, 10, 30, 10, 0));
        assertEquals(LocalDateTime.of(2026, 10, 30, 10, 0), programare.getData());
    }

    @Test
    void getScop() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        Programare programare = new Programare(200, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "aaa");
        assertEquals("aaa", programare.getScop());
    }

    @Test
    void setScop() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        Programare programare = new Programare(200, pacient, LocalDateTime.of(2025, 10, 30, 10, 0), "aaa");
        assertEquals("aaa", programare.getScop());
        programare.setScop("bbb");
        assertEquals("bbb", programare.getScop());
    }
}