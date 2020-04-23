package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User extends ResourceOwner {

    public User(JSONObject data) {
        super(data);
    }

    public static Users fromName(String name, int limit) throws IOException {
        return fromName(name, 1, limit);
    }

    public static Users fromName(String name, int page, int limit) throws IOException {
        JSONObject response = get("/v2/profiles?filter[name]=" + name + "&per_page=" + limit + "&page=" + page);
        assert response != null;
        return new Users(response);
    }

    public static User fromSlug(String slug) throws IOException {
        JSONObject response = get("/v2/profiles/" + slug.toLowerCase());
        assert response != null;
        return new User((JSONObject) response.get("data"));
    }

    public static User fromId(long id) throws IOException {
        JSONObject response = get("/v2/profiles/id/" + id);
        assert response != null;
        return new User((JSONObject) response.get("data"));
    }

    public String getName() {
        return (String) data.get("name");
    }

    public boolean isVerified() {
        return (boolean) data.get("verified");
    }

    public String getAvatar() {
        return (String) data.get("avatar");
    }

    public String getBio() {
        return (String) data.get("bio");
    }

    public String getContact() {
        return (String) data.get("contact");
    }

    public String getDiscordUsername() {
        return (String) data.get("discord");
    }

    public boolean isDiscordVerified() {
        return (boolean) data.get("discord_verified");
    }

    public boolean hasSongodaPlus() {
        return (boolean) data.get("songoda_plus");
    }

    public String getSongodaPlusRole() {
        return (String) data.get("songoda_plus_role");
    }

    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        for (Object p : (JSONArray) data.get("posts")) {
            posts.add(new Post((JSONObject) p));
        }
        return posts;
    }

}
