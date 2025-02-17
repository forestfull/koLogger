package com.forestfull.logger.util;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {

    static Properties loadConfig() {
        final Properties properties = new Properties();
        final String propertiesFile = "application.properties";
        final String yamlFile = "application.yml";
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(propertiesFile);

        try {
            if (inputStream != null) {
                System.out.println("Loading configuration from " + propertiesFile);
                properties.load(inputStream);
            } else {
                inputStream = classLoader.getResourceAsStream(yamlFile);
                if (inputStream != null) {
                    System.out.println("Loading configuration from " + yamlFile);
                    Map<String, Object> yamlMap = new Yaml().loadAs(inputStream, Map.class);
                    flattenMap("", yamlMap, properties);
                }
            }

            if (inputStream != null) inputStream.close();
        } catch (IOException e) {
            System.out.println("No configuration file found in classpath: " + e.getMessage());
        }

        return properties;
    }

    private static void flattenMap(String parentKey, Map<String, Object> map, Properties properties) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = parentKey.isEmpty() ? entry.getKey() : parentKey + "." + entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                flattenMap(key, (Map<String, Object>) value, properties);
            } else {
                properties.setProperty(key, value.toString());
            }
        }
    }
}