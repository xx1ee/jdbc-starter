package dao;

import dto.UserFilter;
import entity.User;
import exception.DaoException;
import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDao implements Dao<Long, User>{
    public static final UserDao INSTANCE = new UserDao();
    private static final String deleteSql = """
            DELETE FROM users WHERE user_id = ?
            """;
    private static final String saveSql = """
                INSERT INTO users(fio, pol)
                VALUES (?, ?)
                """;
    private static final String updateSql = """
            UPDATE users
            SET fio = ?,
            pol = ?
            WHERE user_id = ?
            """;
    private static final String findAll = """
            SELECT user_id,fio,pol
            FROM users
            """;
    private static final String selectSql = findAll +
                """
                WHERE user_id = ?
                """;
    private UserDao() {
    }
    public static final List<User> findAll(UserFilter userFilter) {
        List<Integer> parameters = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        parameters.add(userFilter.limit());
        parameters.add(userFilter.offset());
        var sql = findAll + """
                LIMIT ?
                OFFSET ?
                """;
        try (var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setLong(i + 1, parameters.get(i));
                preparedStatement.setLong(i + 1, parameters.get(i));
            }
            var result = preparedStatement.executeQuery();
            while (result.next()) {
                userList.add(new User(result.getLong("user_id"), result.getString("fio"), result.getString("pol")));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userList;
    }
    public List<User> findAll() {
        try(var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(findAll)) {
            var res = statement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (res.next()) {
                userList.add(new User(res.getLong("user_id"), res.getString("fio"), res.getString("pol")));
            }
            return userList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public Optional<User> findByid(Long id, Connection connection) {
        try (var statement = connection.prepareStatement(selectSql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            User user = null;
            if (result.next()) {
                user = new User();
                user.setUserId(result.getLong("user_id"));
                user.setFio(result.getString("fio"));
                user.setPol(result.getString("pol"));
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public Optional<User> findByid(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(selectSql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            User user = null;
            if (result.next()) {
                user = new User();
                user.setUserId(result.getLong("user_id"));
                user.setFio(result.getString("fio"));
                user.setPol(result.getString("pol"));
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public void update(User user) {
        try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, user.getFio());
            preparedStatement.setString(2, user.getPol());
            preparedStatement.setLong(3, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public boolean delete(Long id) {
        try(var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public User save(User user) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(saveSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFio());
            statement.setString(2, user.getPol());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getLong("user_id"));
            }
            return user;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public static UserDao getInstance() {
        return INSTANCE;
    }
}
