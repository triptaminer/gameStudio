package sk.tuke.gamestudio.services.connectors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Files {

    private Properties prop;

    public Files() {

    }

    public Properties getProp() {

        prop = new Properties();
        String fileName = "psql.conf";
        try (
                FileInputStream fis = new FileInputStream(fileName)) {
            try {
                prop.load(fis);
                return prop;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (
                FileNotFoundException ex) {
            System.err.println("Config file not found!");
        } catch (
                IOException ex) {
            System.err.println("IO issue");
        }
        return null;
    }

}
