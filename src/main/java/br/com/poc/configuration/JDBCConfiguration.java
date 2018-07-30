package br.com.poc.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class JDBCConfiguration {

    private final HikariDataSource dataSource;

    public JDBCConfiguration(AppConfiguration configuration) {
        this.dataSource = initDataSource(configuration.getDatabaseConfiguration());
        Runtime.getRuntime().addShutdownHook(new Thread(this.dataSource::close));
    }

    private HikariDataSource initDataSource(AppConfiguration.DatabaseConfiguration configuration) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(configuration.getJdbcUrl());
        config.setUsername(configuration.getUser());
        config.setPassword(configuration.getPassword());
        config.setMaximumPoolSize(configuration.getMaxSize());
        config.setMinimumIdle(configuration.getMinSize());
        config.addDataSourceProperty("cachePrepStmts", configuration.getEnablePreparedStatementCache());
        config.addDataSourceProperty("prepStmtCacheSize", configuration.getPreparedStatementCacheSize());
        config.addDataSourceProperty("prepStmtCacheSqlLimit", configuration.getPreparedStatementCacheSqlLimit());

        return new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }
}
