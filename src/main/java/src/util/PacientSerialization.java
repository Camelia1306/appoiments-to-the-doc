package util;
import domain.Pacient;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class PacientSerialization {
    public void serialize(Collection<Pacient> pacienti, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(new ArrayList<>(pacienti));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pacient> deserialize(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<Pacient>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu a fost găsit. Se va crea un repository gol.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
