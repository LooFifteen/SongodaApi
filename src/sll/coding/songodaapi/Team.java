package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Team extends ResourceOwner {

    public Team(JSONObject data) {
        super(data);
    }

    public static Team fromSlug(String slug) throws IOException {
        JSONObject response = get("/v2/teams/" + slug);
        assert response != null;
        return new Team((JSONObject) response.get("data"));
    }

    public static Team fromId(long id) throws IOException {
        JSONObject response = get("/v2/teams/id/" + id);
        assert response != null;
        return new Team((JSONObject) response.get("data"));
    }

    public User getOwner() throws IOException {
        return User.fromSlug((String) ((JSONObject) data.get("owner")).get("slug"));
    }

    public String getLogo() {
        return (String) data.get("logo");
    }

    public String getFullLogo() {
        return (String) data.get("logo_full");
    }

    public String getDescription() {
        return (String) data.get("description");
    }

    public String getDiscord() {
        return (String) data.get("discord");
    }

    public List<User> getMembers() throws IOException {
        List<User> members = new ArrayList<>();
        JSONArray array = (JSONArray) data.get("members");
        for (Object m : array) {
            members.add(User.fromSlug((String) ((JSONObject) m).get("slug")));
        }
        return members;
    }

    public List<Resource> getResources() throws IOException {
        List<Resource> resources = new ArrayList<>();
        JSONArray array = (JSONArray) data.get("products");
        for (Object m : array) {
            resources.add(Resource.fromSlug((String) ((JSONObject) m).get("slug")));
        }
        return resources;
    }

}
