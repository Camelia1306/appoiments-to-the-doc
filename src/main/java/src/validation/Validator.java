package validation;

import domain.Pacient;
import domain.Programare;

import java.time.LocalDateTime;
import java.util.Collection;

public class Validator {

    public static void validatePacient(Pacient pacient) {
        if (pacient.getNume() == null || pacient.getNume().trim().isEmpty()) {
            throw new IllegalArgumentException("Numele pacientului nu poate fi gol!");
        }
        if (pacient.getPrenume() == null || pacient.getPrenume().trim().isEmpty()) {
            throw new IllegalArgumentException("Prenumele pacientului nu poate fi gol!");
        }
        if (pacient.getVarsta() <= 0 || pacient.getVarsta() > 120) {
            throw new IllegalArgumentException("Varsta trebuie sa fie intre 1 si 120 de ani!");
        }
    }

    public static void validateProgramare(Programare programare, Collection<Programare> programariExistente) {
        if (programare.getScop() == null || programare.getScop().trim().isEmpty()) {
            throw new IllegalArgumentException("Scopul programării nu poate fi gol!");
        }
        if (programare.getData().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data programarii trebuie să fie în viitor!");
        }

        for (Programare p : programariExistente) {
            if (p.getData().isEqual(programare.getData())) {
                throw new IllegalArgumentException("Există deja o programare la această dată și oră!");
            }
        }
    }
}
