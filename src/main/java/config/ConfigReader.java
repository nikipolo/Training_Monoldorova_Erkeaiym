package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties ;

    public ConfigReader(){
    }


    static {
        try{
            String path = "src/main/resources/config.properties";
            FileInputStream fileInputStream = new FileInputStream(path);
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
    }

    public static String getValue(String key){
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Нет такого ключа в properties: " + key);
        }
        return value.trim();
    }
}
