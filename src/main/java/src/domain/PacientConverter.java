package domain;

public class PacientConverter extends EntitateConverter<Pacient> {

    @Override
    public String toString(Pacient pacient) {
        return pacient.getId() + "," + pacient.getNume() + "," + pacient.getPrenume() + "," + pacient.getVarsta();
    }

    @Override
    public Pacient fromString(String string) {
        String[] tokens = string.split(",");
        if (tokens.length != 4) {
            throw new IllegalArgumentException("String-ul nu este valid pentru conversie în Pacient.");
        }
        int id = Integer.parseInt(tokens[0]);
        String nume = tokens[1];
        String prenume = tokens[2];
        int varsta = Integer.parseInt(tokens[3]);
        return new Pacient(id, nume, prenume, varsta);
    }

}
