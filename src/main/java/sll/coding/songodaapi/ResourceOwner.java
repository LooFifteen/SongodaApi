package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResourceOwner {

    protected final JSONObject data;

    protected ResourceOwner(JSONObject data) {
        this.data = data;
    }

    public long getId() {
        return (long) data.get("id");
    }

    public String getName() {
        return (String) data.get("name");
    }

    public String getSlug() {
        return (String) data.get("slug");
    }

    public String getCover() {
        return (String) data.get("cover");
    }

    public String getLocation() {
        return (String) data.get("location");
    }

    public String getGithub() {
        return (String) data.get("github");
    }

    public String getTwitter() {
        return (String) data.get("twitter");
    }

    public String getWebsite() {
        return (String) data.get("website");
    }

    public String getSupport() {
        return (String) data.get("support");
    }

    public List<Resource> getResources() throws IOException {
        List<Resource> resources = new ArrayList<>();
        for (Object r : (JSONArray) data.get("products")) {
            JSONObject resource = (JSONObject) r;
            resources.add(Resource.fromSlug((String) resource.get("slug")));
        }
        return resources;
    }

    public OwnerType getType() {
        if (this instanceof Team) {
            return OwnerType.TEAM;
        }
        else {
            return OwnerType.USER;
        }
    }

    static JSONObject get(String url) throws IOException {
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

        try {
            return (JSONObject) parser.parse(output);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static String convertStreamToString(InputStream is) {
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
