package dao;

import dto.UserFilter;
import entity.Number;
import entity.User;
import exception.DaoException;
import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NumberDao implements Dao<Long, Number>{
    public static final NumberDao INSTANCE = new NumberDao();
    private static final String deleteSql = """
            DELETE FROM numbers WHERE numbers_id = ?
            """;
    private static final String saveSql = """
                INSERT INTO numbers(number, description)
                VALUES (?, ?)
                """;
    private static final String updateSql = """
            UPDATE numbers
            SET number = ?,
            description = ?
            WHERE numbers_id = ?
            """;
    private static final String findAll = """
            SELECT numbers_id,number,description
            FROM numbers
            """;
    private static final String selectSql = findAll +
            """
            WHERE numbers_id = ?
            """;
    private NumberDao() {
    }

    public Optional<Number> findByid(Long id, Connection connection) {
        try (var statement = connection.prepareStatement(selectSql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            Number number = null;
            if (result.next()) {
                number = new Number();
                number.setNumbers_id(result.getLong("numbers_id"));
                number.setNumber(result.getLong("number"));
                number.setDescription(result.getString("description"));
            }
            return Optional.ofNullable(number);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public static List<Number> findAll(UserFilter userFilter) {
        List<Integer> parameters = new ArrayList<>();
        List<Number> numberList = new ArrayList<>();
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
                numberList.add(new Number(result.getLong("numbers_id"), result.getLong("number"), result.getString("description")));

            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return numberList;
    }
    @Override
    public List<Number> findAll() {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(findAll)) {
            var res = statement.executeQuery();
            List<Number> numberList = new ArrayList<>();
            while (res.next()) {
                numberList.add(new Number(res.getLong("numbers_id"), res.getLong("number"), res.getString("description")));
            }
            return numberList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public Optional<Number> findByid(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(selectSql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            Number number = null;
            if (result.next()) {
                number = new Number();
                number.setNumbers_id(result.getLong("numbers_id"));
                number.setNumber(result.getLong("number"));
                number.setDescription(result.getString("description"));
            }
            return Optional.ofNullable(number);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public void update(Number number) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setLong(1, number.getNumber());
            preparedStatement.setString(2, number.getDescription());
            preparedStatement.setLong(3, number.getNumbers_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public boolean delete(Long id) {
        try(var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    @Override
    public Number save(Number number) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(saveSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, number.getNumber());
            statement.setString(2, number.getDescription());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                number.setNumbers_id(generatedKeys.getLong("numbers_id"));
            }
            return number;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public static NumberDao getInstance() {
        return INSTANCE;
    }
}
