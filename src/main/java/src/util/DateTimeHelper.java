package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateTimeHelper {

    public static LocalDateTime readDateTimeFromInput() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        while (true) {
            // System.out.print("Introduceți data și ora (format: dd-MM-yyyy HH:mm): ");
            String input = scanner.nextLine();

            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Format invalid. Asigurați-va că folosiți formatul corect (dd-MM-yyyy HH:mm).");
            }
        }
    }

    public static void main(String[] args) {
        LocalDateTime dateTime = readDateTimeFromInput();
        System.out.println("Data si ora introduse: " + dateTime);
    }
}
