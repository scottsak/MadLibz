import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World");
        try {
            // Public API:
            // https://www.metaweather.com/api/location/search/?query=<CITY>
            // https://www.metaweather.com/api/location/44418/

            URL url = new URL(
                    "https://api.openweathermap.org/data/2.5/weather?q=London&units=imperial&appid=b0268dbde60ffcf3ed5c0afbeffe1df2");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                // Close the scanner
                scanner.close();

                System.out.println(informationString);

                System.out.println("---------------------");

                // JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parse = new JSONParser();
                Object obj = parse.parse(String.valueOf(informationString));
                System.out.println(obj);
                JSONArray array = new JSONArray();
                array.add(obj);
                System.out.println("---------------------");
                JSONObject weather = (JSONObject) array.get(0);
                // System.out.println(weather);

                // Get the first JSON object in the JSON array
                System.out.println(weather.get("name"));

                

                // JSONObject countryData = (JSONObject) dataObject.get(0);

                // System.out.println(countryData.get("woeid"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
