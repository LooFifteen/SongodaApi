package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

    @Deprecated
    public static List<Resource> fromName(String name) {
        List<Resource> resources = new ArrayList<>();
        JSONObject response = get("/products?filter[name]=" + name + "?per_page=-1");
        JSONArray array = (JSONArray) response.get("data");
        for (Object r : array) {
            resources.add(new Resource((JSONObject) r));
        }
        return resources;
    }

    public static Resource fromSlug(String slug) {
        JSONObject response = get("/products/" + slug.toLowerCase());
        return new Resource((JSONObject) response.get("data"));
    }

    public static List<Resource> myResources(String apiKey) {
        List<Resource> resources = new ArrayList<>();
        JSONObject response = get("/dashboard/products?token=" + apiKey);
        for (Object r : (JSONArray) response.get("data")) {
            resources.add(new Resource((JSONObject) r));
        }
        return resources;
    }

    public long getId() {
        return (long) data.get("id");
    }

    public ResourceOwner getOwner() {
        if (getOwnerType()) {
            return getJars().get(0).getUploadedBy();
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
        if (data.get("team_id") == null) {
            return true;
        } else {
            return false;
        }
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

    @Deprecated
    public String getBody() {
        return (String) data.get("body");
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
        if (clazz.equals("Plugin")) return ResourceType.PLUGIN;
        else if (clazz.equals("Build")) return ResourceType.BUILD;
        else if (clazz.equals("Config")) return ResourceType.CONFIG;
        else if (clazz.equals("Skript")) return ResourceType.SKRIPT;
        else if (clazz.equals("Schematic")) return ResourceType.SCHEMATIC;
        else if (clazz.equals("Website")) return ResourceType.WEBSITE;
        else if (clazz.equals("Libraries / APIs")) return ResourceType.LIBRARY;
        else return ResourceType.UNKNOWN;
    }

    public List<Jar> getJars() {
        List<Jar> jars = new ArrayList<>();
        for (Object jar : (JSONArray) data.get("jars")) {
            jars.add(new Jar((JSONObject) jar));
        }
        return jars;
    }

    public List<Review> getReviewsList() {
        List<Review> reviewsList = new ArrayList<>();
        JSONObject reviewsRes = get("/products/" + getSlug() + "/reviews");
        for (Object r : (JSONArray) reviewsRes.get("data")) {
            reviewsList.add(new Review((JSONObject) r));
        }
        return reviewsList;
    }

    public List<Payment> getPayments(String apiKey) {
        return Payment.fromResource(this, apiKey);
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

            JSONObject json = (JSONObject) parser.parse(output);

            return json;
        } catch (Exception e) {
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
