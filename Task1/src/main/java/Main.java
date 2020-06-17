import DataBase.DBConnection;
import Entity.Weather;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    static public void main(String[] args)
    {
        System.out.println("Create connection to db");
        DBConnection.createConnection();
        Scanner in = new Scanner(System.in);
        System.out.println("Enter region to get info about its weather");
        String regionName = in.nextLine();
        List<Weather> weatherList = DBConnection.getWeatherStatsInRegion(regionName);
        for (Weather w : weatherList) {
            System.out.println(w.toString());
        }

        System.out.println("Enter region to get info about snowy days and the temperature below -10.");
        regionName = in.nextLine();
        List<Date> snowyDates = DBConnection.getDatesWhenSnowAndTempBelow10(regionName);
        for (Date d : snowyDates) {
            System.out.println(d);
        }

        System.out.println("Enter language to get the weather for the last week in the regions where the inhabitants speak a given language.");
        String language = in.nextLine();
        weatherList = DBConnection.getWeatherByLastWeek(language);
        for (Weather w : weatherList) {
            System.out.println(w.toString());
        }

        System.out.println("Get the average temperature for the last week in regions with an area of more than 1000.");
        int temperature = DBConnection.getAverageTemperature();
        System.out.println("Average temperature is : " + temperature);

        System.out.println("Close connection with db");
        DBConnection.close();
    }
}
