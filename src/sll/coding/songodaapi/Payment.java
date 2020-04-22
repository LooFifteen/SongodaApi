package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Payment {

    private final JSONObject data;

    public Payment(JSONObject data) {
        this.data = data;
    }

    public static List<Payment> fromUser(User user, String apiKey) {
        List<Payment> payments = new ArrayList<>();
        JSONObject response = get("/dashboard/payments?filter[username]=" + user.getName() + "&token=" + apiKey);
        JSONArray array = (JSONArray) response.get("data");
        for (Object p : array) {
            payments.add(new Payment((JSONObject) p));
        }
        return payments;
    }

    public static List<Payment> fromResource(Resource resource, String apiKey) {
        List<Payment> payments = new ArrayList<>();
        JSONObject response = get("/dashboard/payments?filter[product]=" + resource.getName() + "&token=" + apiKey);
        JSONArray array = (JSONArray) response.get("data");
        for (Object p : array) {
            payments.add(new Payment((JSONObject) p));
        }
        return payments;
    }

    public String getOrderNumber() {
        return (String) data.get("order_number");
    }

    public String getTransactionId() {
        return (String) data.get("transaction_id");
    }

    public Resource getResource() {
        String name = (String) data.get("product");
        List<Resource> resources = Resource.fromName(name);
        for (Resource r : resources) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }

    public String getStatus() {
        return (String) data.get("status"); // TODO: Replace with Enum Returns
    }

    public String getAmount() {
        return (String) data.get("amount");
    }

    public String getCurrency() {
        return (String) data.get("currency");
    }

    public String getFee() {
        return (String) data.get("fee");
    }

    public String getEmail() {
        return (String) data.get("email");
    }

    public String getFirstName() {
        return (String) data.get("first_name");
    }

    public String getLastName() {
        return (String) data.get("last_name");
    }

    public User getUser() {
        String username = (String) data.get("username");
        List<User> users = User.fromName(username);
        for (User user : users) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public long getCreatedAt() {
        return (long) data.get("created_at");
    }

    public long getUpdatedAt() {
        return (long) data.get("updated_at");
    }

    private static JSONObject get(String url) {
        try {
            String baseUrl = "https://songoda.com/api";
            URL url1 = new URL(baseUrl + url.replaceAll(" ", "%20"));
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "PostmanRuntime/7.24.0");

            if (connection.getResponseCode() != 200) {
                throw new IOException("Failed: Error code " + connection.getResponseCode());
            }

            InputStream in = new BufferedInputStream(connection.getInputStream());
            String output = convertStreamToString(in);

            connection.disconnect();

            JSONParser parser = new JSONParser();

            return (JSONObject) parser.parse(output);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
