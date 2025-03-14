package main.java.tests.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacientTest {

    @Test
    void getId(){
        Pacient pacient = new Pacient(200, "n", "p", 19);
        assertEquals(200, pacient.getId());
    }

    @Test
    void getNume() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        assertEquals("n",pacient.getNume());
    }

    @Test
    void getPrenume() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        assertEquals("p",pacient.getPrenume());
    }

    @Test
    void getVarsta() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        assertEquals(19,pacient.getVarsta());
    }

    @Test
    void setNume() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        assertEquals("n",pacient.getNume());
        pacient.setNume("ana");
        assertEquals("ana",pacient.getNume());

    }

    @Test
    void setPrenume() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        assertEquals("p",pacient.getPrenume());
        pacient.setPrenume("ana");
        assertEquals("ana",pacient.getPrenume());
    }

    @Test
    void setVarsta() {
        Pacient pacient = new Pacient(200,"n", "p", 19);
        assertEquals(19,pacient.getVarsta());
        pacient.setVarsta(19);
        assertEquals(19,pacient.getVarsta());
    }
}