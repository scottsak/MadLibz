import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        try {

            // connect to Madlibz API
            URL url = new URL(
                    "http://madlibz.herokuapp.com/api/random?minlength=5&maxlength=25");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                // Creates a string for the data
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                // Close the scanner
                scanner.close();

                //run through the data 
                JSONParser parse = new JSONParser();
                Object obj = parse.parse(String.valueOf(informationString));
                JSONArray array = new JSONArray();
                array.add(obj);
                JSONObject madLibz = (JSONObject) array.get(0);

                Object blankObjects = parse.parse(String.valueOf(madLibz.get("blanks")));
                
                int blanksSize = ((ArrayList) blankObjects).size();


                Scanner sc = new Scanner(System.in); 

                String[] userInputs = new String[blanksSize];

                //gets user input for all the blanks
                for(int i=0; i< blanksSize; i++){
                    System.out.println("Enter "+((ArrayList) blankObjects).get(i));
                    String x = sc.nextLine();
                    userInputs[i] = x;
                }
                
                Object sentenceObject = parse.parse(String.valueOf(madLibz.get("value")));
                
                int sentenceAmount = ((ArrayList) sentenceObject).size();

                StringBuilder finalMadLibz = new StringBuilder();

                
                //creates the final output
                for(int i=0; i < blanksSize; i++){
                    finalMadLibz.append(((ArrayList) sentenceObject).get(i));
                    finalMadLibz.append(userInputs[i]);
                }
                finalMadLibz.append(((ArrayList) sentenceObject).get(sentenceAmount-2));
                sc.close();

                // Prints the title of the madLibz
                System.out.println(madLibz.get("title"));
                System.out.println("------------------------");
                System.out.println(finalMadLibz);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
