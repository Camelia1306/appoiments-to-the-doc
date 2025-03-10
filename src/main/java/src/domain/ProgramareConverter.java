package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProgramareConverter extends EntitateConverter<Programare> {
    private final PacientConverter pacientConverter = new PacientConverter();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String toString(Programare programare) {
        String pacientString = pacientConverter.toString(programare.getPacient());
        String dataString = programare.getData().format(dateTimeFormatter);
        return programare.getId() + ";" + pacientString + ";" + dataString + ";" + programare.getScop();
    }

    @Override
    public Programare fromString(String string) {
        String[] tokens = string.split(";", 4); // Se așteaptă 4 părți: id_programare, pacient, data, scop
        if (tokens.length != 4) {
            throw new IllegalArgumentException("String-ul nu este valid pentru conversie în Programare.");
        }

        int id = Integer.parseInt(tokens[0]);
        Pacient pacient = pacientConverter.fromString(tokens[1]); // Așteaptă delimitare cu ','
        LocalDateTime data = LocalDateTime.parse(tokens[2], dateTimeFormatter);
        String scop = tokens[3];

        return new Programare(id, pacient, data, scop);
    }


}
