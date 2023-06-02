import exception.DaoException;
import org.postgresql.Driver;
import utils.ConnectionManager;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class JdbcRunner {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            Long id = scanner.nextLong();

        } finally {
            ConnectionManager.closePool();
        }
    }
}
