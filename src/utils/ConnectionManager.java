package utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static BlockingQueue<Connection> POOL;
    private static List<Connection> sourceConnections;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            initConnectionPool();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
    }
    public static Connection get() {
        try {
            return POOL.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static void initConnectionPool() {
        var POOL_SIZE = PropertiesUtil.get("db.pool.size");
        POOL = new ArrayBlockingQueue<>(Integer.parseInt(POOL_SIZE));
        sourceConnections = new ArrayList<>(Integer.parseInt(POOL_SIZE));
        for (int i = 0; i < Integer.parseInt(POOL_SIZE); i++) {
            var connection = open();
            var proxyConnection = Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class},
                    (p, method, objects) -> method.getName().equals("close") ? POOL.add((Connection) p) : method.invoke(connection, objects));
            POOL.add((Connection) proxyConnection);
            sourceConnections.add(connection);
        }
    }

    private static Connection open() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void closePool() {
        for (Connection sourceConnection : sourceConnections) {
            try {
                sourceConnection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
