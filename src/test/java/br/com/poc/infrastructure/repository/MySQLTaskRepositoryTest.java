package br.com.poc.infrastructure.repository;

import br.com.poc.domain.Task;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.MySQLContainer;

import java.time.LocalDate;

@Tag("integration")
public class MySQLTaskRepositoryTest {

    @ClassRule
    private static MySQLContainer mysql = new MySQLContainer("mysql:8.0.11")
            .withDatabaseName("testbase")
            .withUsername("usr")
            .withPassword("pwd");

    private HikariDataSource dataSource;


    private MySQLTaskRepository sut;

    @BeforeAll
    static void init() {
        mysql.withCommand("mysqld --default-authentication-plugin=mysql_native_password");
        mysql.start();
    }

    @AfterAll
    static void destroy() {
        mysql.stop();
    }

    @BeforeEach
    void setUp() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(mysql.getJdbcUrl() + "?TC_INITSCRIPT=schema.sql");
//        config.setJdbcUrl("jdbc:tc:mysql:8.0.11://hostname/databasename?TC_INITSCRIPT=schema.sql");
        config.setUsername(mysql.getUsername());
        config.setPassword(mysql.getPassword());
        dataSource = new HikariDataSource(config);

        sut = new MySQLTaskRepository(dataSource);
    }

    @AfterEach
    void tearDown() {
        dataSource.close();
    }

    @Test
    void save_task_to_database() throws Exception {
        Task task = Task.builder()
                .subject("Subject")
                .description("Description")
                .end(LocalDate.now())
                .start(LocalDate.now())
                .build();

        sut.save(task);
    }

}
