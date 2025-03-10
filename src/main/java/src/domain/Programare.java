package domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Programare extends Entitate implements Serializable {
    private Pacient pacient;
    private LocalDateTime data;
    private String scop;

    private static final long serialVersionUID = 1L;


    public Programare(int id, Pacient pacient, LocalDateTime data, String scop) {
        super(id);
        this.pacient = pacient;
        this.data = data;
        this.scop = scop;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getScop() {
        return scop;
    }

    public void setScop(String scop) {
        this.scop = scop;
    }

    public int getPacientId()
    {return pacient.getId();}

    @Override
    public String toString() {
        return "Programare{" +
                "id=" + getId() +
                " pacient=" + pacient +
                ", data=" + data +
                ", scop='" + scop + '\'' +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Programare that = (Programare) obj;

        return ((Programare) obj).getId() == that.getId() &&
                (pacient != null ? pacient.equals(that.pacient) : that.pacient == null) &&
                (data != null ? data.equals(that.data) : that.data == null) &&
                (scop != null ? scop.equals(that.scop) : that.scop == null);
    }

    @Override
    public int hashCode() {
        int result = this.getId();
        result = 31 * result + (pacient != null ? pacient.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (scop != null ? scop.hashCode() : 0);
        return result;
    }

}

