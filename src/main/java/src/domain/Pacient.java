package domain;

import java.io.Serializable;
import java.util.Objects;

public class Pacient extends Entitate implements Serializable {
    private String nume;
    private String prenume;
    private int varsta;

    private static final long serialVersionUID = 1L;


    public Pacient(int id, String nume, String prenume, int varsta) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.varsta = varsta;
    }

    public Pacient(int id) {
        super(id);
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }
    public int getVarsta() {
        return varsta;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }
    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return "Pacient [name=" + nume + ", surname=" + prenume + ", age=" + varsta + ", id=" + getId() + "]" + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pacient pacient = (Pacient) o;
        return this.getId() == pacient.getId(); // Ensure comparison is based on ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}

