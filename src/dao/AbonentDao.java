package dao;

import entity.Abonent;
import entity.Number;
import exception.DaoException;
import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbonentDao implements Dao<Long, Abonent>{
    private static final AbonentDao INSTANCE = new AbonentDao();

    private static final String deleteSql = """
            DELETE FROM abonent WHERE abonent_id = ?
            """;
    private static final String saveSql = """
                INSERT INTO abonent(number_id, "check", minutes, switch, user_id)
                VALUES (?, ?, ?, ?, ?)
                """;
    private static final String updateSql = """
            UPDATE abonent
            SET number_id = ?,
            "check" = ?,
            minutes = ?,
            switch = ?,
            user_id = ?
            WHERE abonent_id = ?
            """;
    private static final String findAll = """
            SELECT abonent_id, number_id, "check", minutes, switch, user_id
            FROM abonent
            """;
    public static final String findByIdSql = """
            SELECT abonent_id, number_id, "check", minutes, switch, user_id
            FROM abonent
            WHERE abonent_id = ?
            """;
    private AbonentDao() {
    }

    public static AbonentDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Abonent save(Abonent obj) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(saveSql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, obj.getNumber().getNumbers_id());
            preparedStatement.setLong(2, obj.getCheck());
            preparedStatement.setLong(3, obj.getMinutes());
            preparedStatement.setString(4, obj.getSwitchO());
            preparedStatement.setLong(5, obj.getAbonentId());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                obj.setAbonentId(generatedKeys.getLong("abonent_id"));
            }
            return obj;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Abonent> findAll() {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(findAll)) {
            var res = statement.executeQuery();
            List<Abonent> abonentsList = new ArrayList<>();
            while (res.next()) {
                abonentsList.add(new Abonent(res.getLong("abonent_id"), NumberDao.getInstance().findByid(res.getLong("number_id")).orElse(null),
                        res.getLong("check"), res.getInt("minutes"), res.getString("switch"), UserDao.getInstance().findByid(res.getLong("user_id")).orElse(null)));
            }
            return abonentsList;
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
    public void update(Abonent obj) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setLong(1, obj.getNumber().getNumbers_id());
            preparedStatement.setLong(2, obj.getCheck());
            preparedStatement.setLong(3, obj.getMinutes());
            preparedStatement.setString(4, obj.getSwitchO());
            preparedStatement.setLong(5, obj.getAbonentId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Abonent> findByid(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(findByIdSql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            Abonent abonent = null;
            if (result.next()) {
                abonent = new Abonent(
                        result.getLong("abonent_id"),
                        NumberDao.getInstance().findByid(result.getLong("number_id"), connection).orElse(null),
                        result.getLong("check"),
                        result.getInt("minutes"),
                        result.getString("switch"),
                        UserDao.getInstance().findByid(result.getLong("user_id"), connection).orElse(null)
                );
            }
            return Optional.ofNullable(abonent);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Abonent> findByid(long abonent_id, Connection connection) {
        try (var statement = connection.prepareStatement(findByIdSql)) {
            statement.setLong(1, abonent_id);
            var result = statement.executeQuery();
            Abonent abonent = null;
            if (result.next()) {
                abonent = new Abonent(
                        result.getLong("abonent_id"),
                        NumberDao.getInstance().findByid(result.getLong("number_id"), connection).orElse(null),
                        result.getLong("check"),
                        result.getInt("minutes"),
                        result.getString("switch"),
                        UserDao.getInstance().findByid(result.getLong("user_id"), connection).orElse(null)
                );
            }
            return Optional.ofNullable(abonent);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
