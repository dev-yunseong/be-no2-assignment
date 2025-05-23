package dev.yunseong.beno2assignment.repository;

import dev.yunseong.beno2assignment.DBConnector;
import dev.yunseong.beno2assignment.domain.User;
import dev.yunseong.beno2assignment.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DBConnector dbConnector;

    private static final String CREATE_USER = "INSERT INTO " +
            "users(name, email) " +
            "values (?, ?);";

    private static final String GET_USER = "SELECT " +
            "id, name, email " +
            "FROM users;";

    private static final String GET_USER_BY_ID = "SELECT id, name, email FROM users WHERE id = ?;";

    private static final String UPDATE_USER_NAME = "UPDATE users SET name = ? WHERE id = ?;";

    public User createUser(String name, String email) {
        try (Connection connection = dbConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                    return new User(id, name, email);
                } else {
                    throw new SQLException("Failed to create user");
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to create user", e);
        }
    }

    public User updateUserName(Long id, String name) {
        try (Connection connection = dbConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            return getUserById(id);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to update user", e);
        }
    }

    public List<User> getUsers() {
        try (Connection connection = dbConnector.getConnection();
            Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(GET_USER)) {
                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    users.add(new User(id, name, email));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to get users", e);
        }
    }

    public User getUserById(Long id) {
        try (Connection connection = dbConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                    return new User(id, name, email);
                } else {
                    throw new NotFoundException("Failed to get user by id");
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Failed to get user by id", e);
        }
    }
}
