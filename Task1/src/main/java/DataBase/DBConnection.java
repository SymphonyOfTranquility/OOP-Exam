package DataBase;

import Entity.Region;
import Entity.Weather;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBConnection {
    private static Connection currentConnection = null;

    public static boolean createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String login = "postgres";
            String passwd = "qwe";
            currentConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/weather",
                    login, passwd);
            currentConnection.setAutoCommit(true);

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

    public static List<Weather> getWeatherStatsInRegion(String regionName) {
        try {
            String sql = "SELECT x.id, x.regionId, x.date, x.temperature, x.precipitation " +
                     "FROM weather x INNER JOIN region y ON x.region_id = y.id "  +
                     "WHERE x.name = '" + regionName + "';";
            Statement statement = currentConnection.createStatement();
            ResultSet curSet = statement.executeQuery(sql);
            List<Weather> weathers = new LinkedList<Weather>();
            while (curSet.next()) {
                Weather newWeather = new Weather(curSet.getInt("id"), curSet.getInt("region_id"),
                        curSet.getDate("date"), curSet.getInt("temperature"), curSet.getString("precipitation"));
                weathers.add(newWeather);
            }
            return weathers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<Date> getDatesWhenSnowAndTempBelow10(String regionName) {
        try {
            String sql = "SELECT x.date " +
                    "FROM weather x INNER JOIN region y ON x.region_id = y.id "  +
                    "WHERE x.name = '" + regionName + "' AND x.precipitation = 'snow' " +
                    "AND x.temperature < -10;";
            Statement statement = currentConnection.createStatement();
            ResultSet curSet = statement.executeQuery(sql);
            List<Date> dates = new LinkedList<Date>();
            while (curSet.next()) {
                dates.add(curSet.getDate("date"));
            }
            return dates;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<Weather> getWeatherByLastWeek(String language) {
        try {
            String sql = "SELECT x.id, x.regionId, x.date, x.temperature, x.precipitation " +
                    "FROM (weather x INNER JOIN region y ON x.region_id = y.id) " +
                            "INNER JOIN inhabitants z ON z.id = y.inhabitants_id "  +
                    "WHERE z.language = '" + language + "';";

            Statement statement = currentConnection.createStatement();
            ResultSet curSet = statement.executeQuery(sql);
            List<Weather> weathers = new LinkedList<Weather>();
            while (curSet.next()) {
                Weather newWeather = new Weather(curSet.getInt("id"), curSet.getInt("region_id"),
                        curSet.getDate("date"), curSet.getInt("temperature"), curSet.getString("precipitation"));
                weathers.add(newWeather);
            }
            return weathers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static int getAverageTemperature() {
        try {
            String sql = "SELECT AVG(temperature) " +
                    "FROM weather x INNER JOIN region y ON x.region_id = y.id "  +
                    "WHERE y.square > 1000 AND x.date >= date_trunc('week', now()-'7 day'::interval);";

            Statement statement = currentConnection.createStatement();
            ResultSet curSet = statement.executeQuery(sql);
            return curSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }


}
