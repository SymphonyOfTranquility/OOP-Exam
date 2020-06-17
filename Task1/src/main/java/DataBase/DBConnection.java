package DataBase;

import java.sql.*;

public class DBConnection {
    private static Connection currentConnection = null;

    public static boolean createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String login = "postgres";
            String passwd = "qwe";
            currentConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/weather",
                    login, passwd);

            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        currentConnection = null;
        return false;
    }

    public static boolean exists() {
        try {
            if (!currentConnection.isClosed())
                return true;
            else
                return false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static void close() {
        try {
            currentConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //public static void add
}
