package br.com.poc.infrastructure.repository;

import br.com.poc.domain.Task;
import br.com.poc.domain.repository.TaskRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLTaskRepository implements TaskRepository {
    private static final String SAVE_SQL = "INSERT INTO tasks (subject, start_date, end_date, description) VALUES (?,?,?,?)";
    private static final String SELECT_ALL_SQL = "SELECT id, subject, start_date, end_date, description FROM tasks";

    private DataSource dataSource;

    public MySQLTaskRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Task task) throws Exception {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {

            statement.setString(1, task.getSubject());
            statement.setDate(2, Date.valueOf(task.getStart()));
            statement.setDate(3, Date.valueOf(task.getEnd()));
            statement.setString(4, task.getDescription());

            statement.execute();
        }
    }

    @Override
    public List<Task> listAll() throws Exception {
        List<Task> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_SQL)) {

            while (resultSet.next()) {
                Task task = extract(resultSet);
                result.add(task);
            }
        }
        return result;
    }

    private Task extract(ResultSet resultSet) throws SQLException {
        return Task.builder()
                .id(resultSet.getInt("id"))
                .subject(resultSet.getString("subject"))
                .description(resultSet.getString("description"))
                .end(resultSet.getDate("end_date").toLocalDate())
                .start(resultSet.getDate("start_date").toLocalDate())
                .build();
    }
}
