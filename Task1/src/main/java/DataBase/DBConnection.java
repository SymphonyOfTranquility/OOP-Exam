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
/*
            String sql = "SELECT EXISTS ( " +
                    "SELECT FROM information_schema.tables " +
                    "WHERE  table_schema = 'schema_name' " +
                    "AND    table_name   = 'table_name' );";
            Statement statement = currentConnection.createStatement();
            ResultSet curSet = statement.executeQuery(sql);
*/
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
