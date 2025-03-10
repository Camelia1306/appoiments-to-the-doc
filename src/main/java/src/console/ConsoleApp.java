package console;

import domain.Pacient;
import domain.Programare;
import service.PacientService;
import service.ProgramareService;
import util.DateTimeHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;


public class ConsoleApp {
    private static Scanner scanner = new Scanner(System.in);
    private PacientService pacientService;
    private ProgramareService programareService;

    public ConsoleApp(PacientService pacientService, ProgramareService programareService) {
        this.pacientService = pacientService;
        this.programareService = programareService;
    }

    public void menuPacient(){
        System.out.println("1. adauga pacient");
        System.out.println("2. sterge pacient");
        System.out.println("3. update pacient");
        System.out.println("4. afisare pacients");
        int option = scanner.nextInt();
        switch(option){
            case 1: {
                while (true) {
                    try {
                        System.out.println("Introduceți un pacient: nume, prenume, varsta");
                        String nume = scanner.next();
                        String prenume = scanner.next();
                        int varsta = scanner.nextInt();

                        pacientService.addPacient(nume, prenume, varsta);
                        break; // Ieșim din buclă dacă adăugarea a avut succes
                    } catch (IllegalArgumentException e) {
                        System.out.println("Eroare: " + e.getMessage());
                        System.out.println("Vă rugăm să reintroduceți datele corect.");
                        scanner.nextLine();
                    }
                }
                break;
            }
            case 2: {
                System.out.println("id pentru stergere: ");
                int id = scanner.nextInt();
                pacientService.delete(id);
                break;
            }
            case 3: {
                while (true) {
                    try {
                        System.out.print("Introduceți ID-ul pacientului de actualizat: ");
                        int id = scanner.nextInt();

                        System.out.print("Introduceți numele atributului de actualizat (nume/prenume/varsta): ");
                        String attributeName = scanner.next();

                        System.out.print("Introduceți noua valoare pentru " + attributeName + ": ");
                        Object newValue;
                        if (attributeName.equals("varsta")) {
                            newValue = scanner.nextInt();
                        } else {
                            newValue = scanner.next();
                        }
                        pacientService.updateAttribute(id, attributeName, newValue);
                        break;
                    } catch(IllegalArgumentException e) {
                        System.out.println("Eroare"+ e.getMessage());
                        System.out.println("Vă rugăm să reintroduceți datele corect.");
                        scanner.nextLine();
                    }
                }
                break;
            }
            case 4: {
                System.out.println(pacientService.getAll());
                break;
            }
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println("1. Pacient");
                System.out.println("2. Programare");
                System.out.println("3. Exit");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        menuPacient();
                        break;
                    case 2:
                        menuProgramare();
                        break;
                    default:
                        System.out.println("Opțiune invalidă");
                        return;
                }
            }
            catch (Exception e) {
                System.out.println("Eroare neașteptată: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

    private void menuProgramare() {
        System.out.println("1. adauga programare");
        System.out.println("2. sterge programare");
        System.out.println("3. update programare");
        System.out.println("4. afisare programare");
        System.out.println("5.Numărul de programări pentru fiecare pacient în parte");
        System.out.println("6.Numărul total de programări pentru fiecare lună a anului");
        System.out.println("7.Numărul de zile trecute de la ultima programare a fiecărui pacient");
        System.out.println("8.Cele mai aglomerate luni ale anului");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch(option){
            case 1: {
                while (true) {
                    try {
                        System.out.println("Inserati o programare: pacient(id; nume; prenume; varsta), scop, data");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        String nume = scanner.next();
                        scanner.nextLine();

                        String prenume = scanner.next();
                        scanner.nextLine();

                        int varsta = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Introduceți scopul programării:");
                        String scop = scanner.nextLine();

                        System.out.println("Introduceți data programării");
                        LocalDateTime data = DateTimeHelper.readDateTimeFromInput();

                        Pacient p = new Pacient(id, nume, prenume, varsta);

                        programareService.addProgramare(p, data, scop);

                        System.out.println("Programare adăugată cu succes!");
                        break;
                    } catch (IllegalArgumentException | DateTimeParseException e) {
                        System.out.println("Eroare: " + e.getMessage());
                        System.out.println("Vă rugăm să reintroduceți datele corect.");
                        scanner.nextLine();
                    }
                }
                break;
            }
            case 2: {
                System.out.println("id pentru stergere: ");
                int id = scanner.nextInt();
                programareService.delete(id);
                break;
            }
            case 3: {
                while (true) {
                    try {
                        System.out.print("Introduceți ID-ul programării de actualizat: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Introduceți numele atributului de actualizat (pacient/scop/data): ");
                        String attributeName = scanner.nextLine();

                        System.out.print("Introduceți noua valoare pentru " + attributeName + ": ");
                        Object newValue;

                        if (attributeName.equals("pacient")) {
                            System.out.print("Introduceți ID-ul noului pacient: ");
                            int pacientId = scanner.nextInt();
                            scanner.nextLine();

                            Pacient pacientNou = pacientService.get(pacientId);

                            if (pacientNou != null) {
                                newValue = pacientNou;
                            } else {
                                System.out.println("Pacientul cu ID-ul specificat nu există.");
                                continue;
                            }
                        } else if (attributeName.equals("data")) {
                            System.out.println("Introduceți noua dată");
                            newValue = DateTimeHelper.readDateTimeFromInput();
                        } else {
                            newValue = scanner.nextLine();
                        }

                        programareService.updateAttribute(id, attributeName, newValue);
                        System.out.println("Actualizare efectuată cu succes!");
                        break;

                    } catch (IllegalArgumentException | DateTimeParseException e) {
                        System.out.println("Eroare: " + e.getMessage());
                        System.out.println("Vă rugăm să reintroduceți datele corect.");
                        scanner.nextLine();
                    }
                }
                break;
            }

            case 4: {
                System.out.println(programareService.getAll());
                break;
            }
            case 5: {
                System.out.println(programareService.numarProgramariPerPacient((List<Programare>) programareService.getAll()));
            }
            case 6: {
                System.out.println(programareService.numarProgramariPerLuna((List<Programare>) programareService.getAll()));
            }
            case 7: {
                System.out.println(programareService.zileDeLaUltimaProgramare((List<Programare>) programareService.getAll()));
            }
            case 8: {
                System.out.println(programareService.celeMaiAglomerateLuni((List<Programare>) programareService.getAll()));
            }
        }
    }

}

