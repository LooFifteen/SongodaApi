package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Users extends SearchResults {

    private final JSONArray data;

    public Users(JSONObject response) {
        super((JSONObject) response.get("links"), (JSONObject) response.get("meta"));
        data = (JSONArray) response.get("data");
    }

    public List<User> getResults() {
        List<User> users = new ArrayList<>();
        for (Object o : data) {
            users.add(new User((JSONObject) o));
        }
        return users;
    }

}
