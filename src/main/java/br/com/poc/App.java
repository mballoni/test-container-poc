package br.com.poc;

import br.com.poc.configuration.AppConfiguration;
import br.com.poc.configuration.JDBCConfiguration;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class App implements Runnable {

    public static void main(String[] args) {
        AppConfiguration configuration = new AppConfiguration(ConfigFactory.load());

        App app = new App(configuration);
        new Thread(app).start();

        app.stop();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private final AppConfiguration configuration;
    private final CountDownLatch latch;

    App(AppConfiguration configuration) {
        this.configuration = configuration;
        this.latch = new CountDownLatch(1);
    }

    void start() {
        LOGGER.info("Starting application...");

        JDBCConfiguration jdbcConfiguration = new JDBCConfiguration(configuration);

        park();

        LOGGER.info("Shutting down application!");
    }

    private void park() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            LOGGER.error("Latch error", e);
        }
    }

    void stop() {
        latch.countDown();
    }

    @Override
    public void run() {
        this.start();
    }
}
