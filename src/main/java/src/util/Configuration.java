package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private Properties properties = new Properties();

    public Configuration(String filename) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Nu s-a putut citi fișierul de setări!", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
