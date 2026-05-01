package net.dimmid.config;

import net.dimmid.Main;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Config {
    private static final Logger logger = LoggerFactory.getLogger(Config.class);
    private static final Marker CONSOLE_ONLY = MarkerFactory.getMarker("CONSOLE_ONLY");
    private static final String CONFIG_FILE_NAME = "config_dev.properties";
    private static final Properties properties = new Properties();
    private static Config instance = null;

    private Config() {
    }

    public static Config load() {
        if (instance != null) {
            return instance;
        }
        synchronized (Config.class) {
            if (instance == null) {
                instance = new Config();
                try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
                    properties.load(inputStream);
                } catch (IOException e) {
                    logger.error("Failed to load config", e);
                }
                applyLoggingLevel();
            }
            return instance;
        }
    }

    public static Optional<String> getProperty(String key) {
        if (instance == null) {
            instance = Config.load();
        }
        String env = System.getenv(key);
        if (env != null && !env.isBlank()) {
            return Optional.of(env);
        }
        env = properties.getProperty(convertEnvTypeNamingToPropertyNaming(key));
        if (env == null || env.isBlank()) {
            env = null;
        }
        return Optional.ofNullable(env);
    }

    public static String getOrDefault(String key, String defaultValue) throws FileNotFoundException {
        return getProperty(key).orElse(defaultValue);
    }

    private static void applyLoggingLevel() {
        String debugMode = properties.getProperty("debug", "false");

        if ("true".equalsIgnoreCase(debugMode)) {
            Configurator.setLevel("net.dimmid", Level.DEBUG);
            logger.info(CONSOLE_ONLY, "Debug mode enabled. Logging level set to DEBUG for net.dimmid");
        }
    }

    private static String convertEnvTypeNamingToPropertyNaming(String key) {
        return key.toLowerCase().replace("_", ".");
    }
}
