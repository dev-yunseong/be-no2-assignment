package dev.yunseong.beno2assignment.repository;

import dev.yunseong.beno2assignment.DBConnector;
import dev.yunseong.beno2assignment.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

@RequiredArgsConstructor
@Repository
public class ScheduleRepository {
    private static final String CREATE_SCHEDULE = "INSERT INTO schedules(title, content, password, uid) VALUES (?, ?, ?, ?)";
    private static final String GET_SCHEDULE = "SELECT id, title, content, password, uid, createdAt, updatedAt FROM schedules WHERE id = ?";
    private static final String GET_SCHEDULES = "SELECT id, title, content, password, uid, createdAt, updatedAt FROM schedules ORDER BY updatedAt DESC";
    private static final String GET_SCHEDULES_BY_UID = "SELECT id, title, content, password, uid, createdAt, updatedAt FROM schedules WHERE uId = ? ORDER BY updatedAt DESC";
    private static final String GET_SCHEDULES_BY_DATE = "SELECT id, title, content, password, uid, createdAt, updatedAt FROM schedules WHERE DATE(updatedAt) = ? ORDER BY updatedAt DESC";
    private static final String GET_SCHEDULES_BY_UID_AND_DATE = "SELECT id, title, content, password, uid, createdAt, updatedAt FROM schedules WHERE uId = ? AND DATE(updatedAt) = ? ORDER BY updatedAt DESC";
    private static final String UPDATE_SCHEDULE = "UPDATE schedules SET content = ? WHERE id = ?";
    private static final String DELETE_SCHEDULE = "DELETE FROM schedules WHERE id = ?";
    private final DBConnector dbConnector;

    public Schedule createSchedule(String title, String content, String password, Long userId) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SCHEDULE, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, content);
            preparedStatement.setString(3, password);
            preparedStatement.setLong(4, userId);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                    return getSchedule(id);
                } else {
                    throw new IllegalArgumentException("Failed to create schedule");
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to create schedule", e);
        }
    }

    public Schedule getSchedule(Long id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SCHEDULE)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Long scheduleId = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    String password = resultSet.getString("password");
                    Long userId = resultSet.getLong("uid");
                    LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
                    LocalDateTime updatedAt = resultSet.getTimestamp("updatedAt").toLocalDateTime();
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                    return new Schedule(scheduleId, title, content, password, userId, createdAt, updatedAt);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to get schedule", e);
        }
        return null;
    }

    public Schedule updateSchedule(Long id, String content) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SCHEDULE)) {
            preparedStatement.setString(1, content);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            return getSchedule(id);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to update schedule", e);
        }
    }

    public String getPassword(Long id) {
        return getSchedule(id).getPassword();
    }

    public void deleteSchedule(Long id) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SCHEDULE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to delete schedule", e);
        }
    }

    public List<Schedule> getSchedules() {
        try (Connection connection = dbConnector.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(GET_SCHEDULES)) {
                List<Schedule> schedules = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    String password = resultSet.getString("password");
                    Long userId = resultSet.getLong("uid");
                    LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
                    LocalDateTime updatedAt = resultSet.getTimestamp("updatedAt").toLocalDateTime();
                    schedules.add(new Schedule(id, title, content, password, userId, createdAt, updatedAt));
                }
                resultSet.close();
                statement.close();
                connection.close();
                return schedules;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to get schedules", e);
        }
    }

    public List<Schedule> getSchedulesByUserId(Long userId) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SCHEDULES_BY_UID)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Schedule> schedules = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    String password = resultSet.getString("password");
                    LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
                    LocalDateTime updatedAt = resultSet.getTimestamp("updatedAt").toLocalDateTime();
                    schedules.add(new Schedule(id, title, content, password, userId, createdAt, updatedAt));
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return schedules;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to get schedules by userId", e);
        }
    }

    public List<Schedule> getSchedulesByDate(Date date) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SCHEDULES_BY_DATE)) {
            preparedStatement.setDate(1, date);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Schedule> schedules = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    String password = resultSet.getString("password");
                    Long userId = resultSet.getLong("uid");
                    LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
                    LocalDateTime updatedAt = resultSet.getTimestamp("updatedAt").toLocalDateTime();
                    schedules.add(new Schedule(id, title, content, password, userId, createdAt, updatedAt));
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return schedules;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to get schedules by date", e);
        }
    }

    public List<Schedule> getSchedulesByUserIdAndDate(Long userId, Date date) {
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SCHEDULES_BY_UID_AND_DATE)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setDate(2, date);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Schedule> schedules = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String title = resultSet.getString("title");
                    String content = resultSet.getString("content");
                    String password = resultSet.getString("password");
                    LocalDateTime createdAt = resultSet.getTimestamp("createdAt").toLocalDateTime();
                    LocalDateTime updatedAt = resultSet.getTimestamp("updatedAt").toLocalDateTime();
                    schedules.add(new Schedule(id, title, content, password, userId, createdAt, updatedAt));
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
                return schedules;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to get schedules by userId and date", e);
        }
    }
}
