package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User extends ResourceOwner {

    public User(JSONObject data) {
        super(data);
    }

    public static List<User> fromName(String name) {
        List<User> users = new ArrayList<>();
        JSONObject response = get("/v2/profiles?filter[name]=" + name);
        for (Object u : (JSONArray) response.get("data")) {
            users.add(new User((JSONObject) u));
        }
        return users;
    }

    public static User fromSlug(String slug) {
        JSONObject response = get("/v2/profiles/" + slug.toLowerCase());
        return new User((JSONObject) response.get("data"));
    }

    public static User fromId(long id) {
        JSONObject response = get("/v2/profiles/id/" + id);
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

    public List<Post> getPosts() {
        List<Post> posts = new ArrayList<>();
        for (Object p : (JSONArray) data.get("posts")) {
            posts.add(new Post((JSONObject) p));
        }
        return posts;
    }

}
