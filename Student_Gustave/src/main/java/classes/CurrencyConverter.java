package classes;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter implements Serializable {

    private final String apiKey = "aeee39e1bfb33b8afa1a5fbeaa05fe1f"; // Replace with your API key

    public float getExchangeRate(String fromCurrency, String toCurrency) {
        String urlString = "https://api.exchangeratesapi.io/latest?base=" + fromCurrency + "&symbols=" + toCurrency + "&access_key=" + apiKey;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONObject rates = jsonObject.getJSONObject("rates");
            return rates.getFloat(toCurrency);
        } catch (Exception e) {
            e.printStackTrace();
            // Implement fallback logic here
            return 0; // Default/fallback exchange rate
        }
    }
}
