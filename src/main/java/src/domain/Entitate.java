package domain;

import java.io.Serializable;

public abstract class Entitate implements Serializable {
    private final int id;

    private static final long serialVersionUID = 1L;


    public Entitate(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
