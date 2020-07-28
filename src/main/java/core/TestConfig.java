package core;

import java.io.*;
import java.util.Properties;

public class TestConfig {

    private static File resourcesDirectory = new File("src/test/resources");
    private static InputStream envConfig;
    private static Properties properties;

    public static void load (String environment) throws Exception {
        envConfig = new FileInputStream(resourcesDirectory+"/config."+environment+".properties");
        properties = new Properties();
        properties.load(envConfig);
    }

    public static String getProperty(String key){
        if(properties.containsKey(key)){
            return properties.getProperty(key);
        }
        return null;
    }

}
