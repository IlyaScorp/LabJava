package utils;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by dmitry on 28.02.2018.
 */
public class PropertyLoader {

    private static Properties properties;

    private PropertyLoader() {
    }

    private static void load() {
        properties = new Properties();
        try {
            properties.load(PropertyLoader.class.getClassLoader().getResourceAsStream("test.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        if (Objects.isNull(properties)) {
            load();
        }
        return properties.getProperty(key);
    }

}
