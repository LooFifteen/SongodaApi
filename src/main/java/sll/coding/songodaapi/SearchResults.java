package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchResults<T extends Searchable> {

    private final JSONArray data;
    private final JSONObject links;
    private final JSONObject meta;

    private final Class<T> type;

    public SearchResults(Class<T> type, JSONObject data) {
        this.type = type;

        this.data = (JSONArray) data.get("data");
        this.links = (JSONObject) data.get("links");
        this.meta = (JSONObject) data.get("meta");
    }

    public String getFirstPageUrl() {
        return (String) links.get("first");
    }

    public String getLastPageUrl() {
        return (String) links.get("last");
    }

    public String getPreviousPageUrl() {
        return (String) links.get("prev");
    }

    public String getNextPageUrl() {
        return (String) links.get("next");
    }

    public int getCurrentPage() {
        return (int) ((long) meta.get("current_page"));
    }

    public int getFirstResult() {
        return (int) ((long) meta.get("from"));
    }

    public int getLastPage() {
        return (int) ((long) meta.get("last_page"));
    }

    public String getPath() {
        return (String) meta.get("path");
    }

    public String getPerPage() {
        return (String) meta.get("per_page");
    }

    public int getLastResult() {
        return (int) ((long) meta.get("to"));
    }

    public int getTotalResults() {
        return (int) ((long) meta.get("total"));
    }

    public List<T> getResults() {
        List<T> results = new ArrayList<>();
        for (Object o : data) {
            try {
                results.add(type.getDeclaredConstructor(JSONObject.class).newInstance(o));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public SearchResults<T> nextPage() throws IOException {
        JSONObject response = get(getNextPageUrl());
        assert response != null;
        return new SearchResults<>(type, response);
    }

    public SearchResults<T> previousPage() throws IOException {
        JSONObject response = get(getPreviousPageUrl());
        assert response != null;
        return new SearchResults<>(type, response);
    }

    static JSONObject get(String url) throws IOException {
        URL url1 = new URL(url.replaceAll(" ", "%20"));
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
