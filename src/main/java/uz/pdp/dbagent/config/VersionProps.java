package uz.pdp.dbagent.config;

import jakarta.annotation.Nullable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Properties;

@Slf4j
public class VersionProps {

    private static final String FILE_PATH = "src/main/resources/version.properties";

    @SneakyThrows
    private static Properties getProperties() {
        Properties properties = new Properties();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                properties.load(reader);
            }
        }
        return properties;
    }

    @SneakyThrows
    private static void saveProperties(Properties properties) {
        try (FileOutputStream output = new FileOutputStream(FILE_PATH)) {
            properties.store(output, "Updated by VersionProps");
        }
    }

    // === GET METHODS ===
    public static String getAgentId() {
        return getProperties().getProperty("agentId");
    }

    public static Integer getVersion() {
        String version = getProperties().getProperty("version");
        return version != null ? Integer.parseInt(version) : null;
    }

    // === SET METHODS ===
    public static void setAgentId(@Nullable String agentId) {
        if (agentId == null || agentId.isBlank()) {
            throw new IllegalArgumentException("agentId cannot be null or empty");
        }
        updateProperty("agentId", agentId);
    }

    public static void setVersion(String version) {
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("version cannot be null or empty");
        }
        updateProperty("version", version);
    }

    // Universal update method
    private static void updateProperty(String key, String value) {
        Properties props = getProperties();
        props.setProperty(key, value);
        saveProperties(props);
    }
}
