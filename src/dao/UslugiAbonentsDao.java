package dao;

import entity.Abonent;
import entity.UslugiAbonents;
import exception.DaoException;
import utils.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UslugiAbonentsDao implements Dao<Long, UslugiAbonents>{
    private static final UslugiAbonentsDao INSTANCE = new UslugiAbonentsDao();

    private static final String deleteSql = """
            IF (SELECT switch = 'on' from abonent where abonent_id = idd) THEN
                DELETE FROM uslugi_abonents WHERE abonent_id = ? AND uslugi_id = ?;
            """;
    private static final String deleteSqlAdmin = """
            DELETE FROM uslugi_abonents WHERE id = ?;
            """;
    private static final String saveSql = """
            IF (SELECT switch = 'on' from abonent where abonent_id = ?) THEN
                insert into uslugi_abonents(uslugi_id, abonent_id) values (?, ?);
                """;
    private static final String updateSql = """
            UPDATE uslugi_abonents
            SET uslugi_id = ?,
            abonent_id = ?
            WHERE id = ?
            """;
    private static final String findAll = """
            SELECT id, uslugi_id, abonent_id
            FROM uslugi_abonents
            """;
    private static final String findByIdSql = findAll +
            """
            WHERE id = ?
            """;

    private UslugiAbonentsDao() {
    }

    public static UslugiAbonentsDao getInstance() {
        return INSTANCE;
    }

    @Override
    public UslugiAbonents save(UslugiAbonents obj) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(saveSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, obj.getAbonent().getAbonentId());
            statement.setLong(2, obj.getUslugi().getUslugi_id());
            statement.setLong(3, obj.getAbonent().getAbonentId());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                obj.setId(generatedKeys.getLong("id"));
            }
            return obj;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<UslugiAbonents> findAll() {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(findAll)) {
            var result = statement.executeQuery();
            List<UslugiAbonents> abonentsList = new ArrayList<>();
            while (result.next()) {
                abonentsList.add(new UslugiAbonents(result.getLong("id"),
                        UslugiDao.getInstance().findByid(result.getLong("uslugi_id"), connection).orElse(null),
                        AbonentDao.getInstance().findByid(result.getLong("abonent_id"), connection).orElse(null)));
            }
            return abonentsList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try(var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(deleteSqlAdmin)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public boolean delete(Long id_a, Long id_u) {
        try(var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setLong(1, id_a);
            preparedStatement.setLong(2, id_u);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(UslugiAbonents obj) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setLong(1, obj.getUslugi().getUslugi_id());
            preparedStatement.setLong(2, obj.getAbonent().getAbonentId());
            preparedStatement.setLong(3, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<UslugiAbonents> findByid(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(findByIdSql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            UslugiAbonents abonent = null;
            if (result.next()) {
                abonent = new UslugiAbonents(
                        result.getLong("id"),
                        UslugiDao.getInstance().findByid(result.getLong("uslugi_id"), connection).orElse(null),
                        AbonentDao.getInstance().findByid(result.getLong("abonent_id"), connection).orElse(null)
                );
            }
            return Optional.ofNullable(abonent);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
