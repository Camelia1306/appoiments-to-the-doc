package util;

import domain.Programare;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class ProgramareSerializer {
    public void serialize(Collection<Programare> programari, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(programari);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Programare> deserialize(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<Programare>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Fișierul nu a fost găsit. Se va crea un repository gol.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

