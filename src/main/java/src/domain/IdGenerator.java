package domain;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IdGenerator {
    private int currentId;
    private final String filePath;

    public IdGenerator(String filePath) {
        this.filePath = filePath;
        this.currentId = loadLastId();
    }

    private int loadLastId() {
        try {
            if (!Files.exists(Paths.get(filePath))) {
                return 100;
            }
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            reader.close();
            return Integer.parseInt(line);
        } catch (IOException | NumberFormatException e) {
            return 100;
        }
    }

    private void saveLastId() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(String.valueOf(currentId));
            writer.close();
        } catch (IOException e) {
            System.out.println("Eroare la salvarea ID-ului în fișier: " + e.getMessage());
        }
    }

    public int generateId() {
        currentId++;
        saveLastId();
        return currentId;
    }

    public int getId() {
        return currentId;
    }
}
