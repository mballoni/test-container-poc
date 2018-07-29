package br.com.poc;

import br.com.poc.configuration.AppConfiguration;
import br.com.poc.configuration.JDBCConfiguration;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    public static void main(String[] args) {
        AppConfiguration configuration = new AppConfiguration(ConfigFactory.load());
        App app = new App();
        app.start(configuration);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    void start(AppConfiguration configuration) {
        LOGGER.info("Starting application...");
        JDBCConfiguration jdbcConfiguration = new JDBCConfiguration(configuration);

    }
}
