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

public class Resource {

    private final JSONObject data;

    public Resource(JSONObject data) {
        this.data = data;
    }

    public static List<Resource> fromName(String name, int limit) throws IOException {
        List<Resource> resources = new ArrayList<>();
        JSONObject response = get("/v2/products?filter[name]=" + name + "&per_page=" + limit);
        assert response != null;
        JSONArray array = (JSONArray) response.get("data");
        for (Object r : array) {
            resources.add(new Resource((JSONObject) r));
        }
        return resources;
    }

    public static Resource fromSlug(String slug) throws IOException {
        JSONObject response = get("/v2/products/" + slug.toLowerCase());
        assert response != null;
        return new Resource((JSONObject) response.get("data"));
    }

    public static List<Resource> myResources(String apiKey, int limit) throws IOException {
        List<Resource> resources = new ArrayList<>();
        JSONObject response = get("/dashboard/products?token=" + apiKey + "&per_page=" + limit);
        assert response != null;
        for (Object r : (JSONArray) response.get("data")) {
            resources.add(new Resource((JSONObject) r));
        }
        return resources;
    }

    public static Resource fromId(long id) throws IOException {
        JSONObject response = get("/v2/products/id/" + id);
        assert response != null;
        return new Resource((JSONObject) response.get("data"));
    }

    public long getId() {
        return (long) data.get("id");
    }

    public ResourceOwner getOwner() throws IOException {
        if (getOwnerType()) {
            return User.fromId(getOwnerId());
        } else {
            return Team.fromId(getOwnerId());
        }
    }

    private long getOwnerId() {
        if (data.get("team_id") == null) {
            return (long) data.get("user_id");
        } else {
            return (long) data.get("team_id");
        }
    }

    private boolean getOwnerType() {
        return data.get("team_id") == null;
    }

    public String getName() {
        return (String) data.get("name");
    }

    public String getSlug() {
        return (String) data.get("slug");
    }

    public long getDownloads() {
        return (long) data.get("downloads");
    }

    public double getPrice() {
        if (data.get("price") instanceof Double) {
            return (double) data.get("price");
        } else {
            return (long) data.get("price");
        }
    }

    public long getViews() {
        return (long) data.get("views");
    }

    public String getBanner() {
        return (String) data.get("banner");
    }

    public String getBodyBBCode() {
        return (String) ((JSONObject) data.get("body")).get("bbcode");
    }

    public String getBodyHTML() {
        return (String) ((JSONObject) data.get("body")).get("html");
    }

    public String getCurrency() {
        return (String) data.get("currency");
    }

    public String getDescription() {
        return (String) data.get("description");
    }

    public String getIcon() {
        return (String) data.get("icon");
    }

    public String getTagline() {
        return (String) data.get("tagline");
    }

    public String getRating() {
        return (String) data.get("rating");
    }

    public long getReviews() {
        return (long) data.get("reviews");
    }

    public long getCreatedAt() {
        return (long) data.get("created_at");
    }

    public long getUpdatedAt() {
        return (long) data.get("updated_at");
    }

    public ResourceStatus getStatus() {
        String s = (String) data.get("status");
        if (s.equals("approved")) return ResourceStatus.APPROVED;
        else if (s.equals("pending")) return ResourceStatus.PENDING;
        else return ResourceStatus.DECLINED;
    }

    public String getUrl() {
        return (String) data.get("url");
    }

    public ResourceType getType() {
        String clazz = (String) data.get("class");
        switch (clazz) {
            case "Plugin":
                return ResourceType.PLUGIN;
            case "Build":
                return ResourceType.BUILD;
            case "Config":
                return ResourceType.CONFIG;
            case "Skript":
                return ResourceType.SKRIPT;
            case "Schematic":
                return ResourceType.SCHEMATIC;
            case "Website":
                return ResourceType.WEBSITE;
            case "Libraries / APIs":
                return ResourceType.LIBRARY;
            default:
                return ResourceType.UNKNOWN;
        }
    }

    public List<Version> getVersions() {
        List<Version> versions = new ArrayList<>();
        for (Object jar : (JSONArray) data.get("versions")) {
            versions.add(new Version((JSONObject) jar));
        }
        return versions;
    }

    public List<Review> getReviewsList() throws IOException {
        List<Review> reviewsList = new ArrayList<>();
        JSONObject reviewsRes = get("/products/" + getSlug() + "/reviews");
        assert reviewsRes != null;
        for (Object r : (JSONArray) reviewsRes.get("data")) {
            reviewsList.add(new Review((JSONObject) r));
        }
        return reviewsList;
    }

    public List<Payment> getPayments(String apiKey) throws IOException {
        return Payment.fromResource(this, apiKey, -1);
    }

    private static JSONObject get(String url) throws IOException {
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
