package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Resources extends SearchResults {

    private final JSONArray data;

    public Resources(JSONObject response) {
        super((JSONObject) response.get("links"), (JSONObject) response.get("meta"));
        data = (JSONArray) response.get("data");
    }

    public List<Resource> getResults() {
        List<Resource> resources = new ArrayList<>();
        for (Object o : data) {
            resources.add(new Resource((JSONObject) o));
        }
        return resources;
    }

}
