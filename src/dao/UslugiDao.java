package dao;

import dto.UserFilter;
import entity.Number;
import entity.Uslugi;
import exception.DaoException;
import utils.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UslugiDao {
    public static final UslugiDao INSTANCE = new UslugiDao();
    private static final String deleteSql = """
            DELETE FROM "Uslugi" WHERE uslugi_id = ?
            """;
    private static final String saveSql = """
                INSERT INTO "Uslugi"(name_uslugi, tarif)
                VALUES (?, ?)
                """;
    private static final String updateSql = """
            UPDATE "Uslugi"
            SET name_uslugi = ?,
            tarif = ?
            WHERE uslugi_id = ?
            """;
    private static final String findAll = """
            SELECT uslugi_id,name_uslugi,tarif
            FROM "Uslugi"
            """;
    private static final String selectSql = findAll +
            """
            WHERE uslugi_id = ?
            """;
    private UslugiDao() {
    }
    public static final List<Uslugi> findAll(UserFilter userFilter) {
        List<Integer> parameters = new ArrayList<>();
        List<Uslugi> uslugiList = new ArrayList<>();
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
                uslugiList.add(new Uslugi(result.getLong("uslugi_id"), result.getString("name_uslugi"), result.getLong("tarif")));

            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return uslugiList;
    }
    public static List<Uslugi> findAll() {
        try(var connection = ConnectionManager.get();
            var statement = connection.prepareStatement(findAll)) {
            var res = statement.executeQuery();
            List<Uslugi> uslugiList = new ArrayList<>();
            while (res.next()) {
                uslugiList.add(new Uslugi(res.getLong("uslugi_id"), res.getString("name_uslugi"), res.getLong("tarif")));

            }
            return uslugiList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public static Optional<Uslugi> findByid(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(selectSql)) {
            statement.setLong(1, id);
            var result = statement.executeQuery();
            Uslugi uslugi = null;
            if (result.next()) {
                uslugi = new Uslugi();
                uslugi.setUslugi_id(result.getLong("uslugi_id"));
                uslugi.setName_uslugi(result.getString("name_uslugi"));
                uslugi.setTarif(result.getLong("tarif"));
            }
            return Optional.ofNullable(uslugi);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public static void update(Uslugi uslugi) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(updateSql)) {
            preparedStatement.setString(1, uslugi.getName_uslugi());
            preparedStatement.setLong(2, uslugi.getTarif());
            preparedStatement.setLong(3, uslugi.getUslugi_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public static boolean delete(Long id) {
        try(var connection = ConnectionManager.get();
            var preparedStatement = connection.prepareStatement(deleteSql)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public static Uslugi save(Uslugi uslugi) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(saveSql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, uslugi.getName_uslugi());
            statement.setLong(2, uslugi.getTarif());
            statement.executeUpdate();
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                uslugi.setUslugi_id(generatedKeys.getLong("uslugi_id"));
            }
            return uslugi;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
    public static UslugiDao getInstance() {
        return INSTANCE;
    }
}
