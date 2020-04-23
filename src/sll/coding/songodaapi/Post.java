package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Post {

    private final JSONObject data;

    public Post(JSONObject data) {
        this.data = data;
    }

    public long getId() {
        return (long) data.get("id");
    }

    public User getAuthor() throws IOException {
        return User.fromSlug((String) ((JSONObject) data.get("author")).get("slug"));
    }

    public long getUpvotes() {
        return (long) ((JSONObject) data.get("votes")).get("up");
    }

    public long getDownvotes() {
        return (long) ((JSONObject) data.get("votes")).get("down");
    }

    public String getPlain() {
        return (String) ((JSONObject) data.get("body")).get("plain");
    }

    public String getBBCode() {
        return (String) ((JSONObject) data.get("body")).get("bbcode");
    }

    public String getHTML() {
        return (String) ((JSONObject) data.get("body")).get("html");
    }

    public long getCreatedAt() {
        return (long) data.get("created_at");
    }

    public long getUpdatedAt() {
        return (long) data.get("updated_at");
    }

    public long getEditedAt() {
        return (long) data.get("edited_at");
    }

    public List<Post> getReplies() {
        List<Post> replies = new ArrayList<>();
        JSONArray array = (JSONArray) data.get("replies");
        for (Object r : array) {
            replies.add(new Post((JSONObject) r));
        }
        return replies;
    }

}
