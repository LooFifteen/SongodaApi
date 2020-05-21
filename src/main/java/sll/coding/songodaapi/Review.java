package sll.coding.songodaapi;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

public class Review {

    private final JSONObject data;

    public Review(JSONObject data) {
        this.data = data;
    }

    public long getId() {
        return (long) data.get("id");
    }

    public User getReviewer() throws IOException {
        List<User> users = User.fromName((String) data.get("reviewer"), -1).getResults();
        for (User user : users) {
            if (user.getName().equals(data.get("reviewer"))) {
                return user;
            }
        }
        return null;
    }

    public double getRating() {
        if (data.get("rating") instanceof Double) {
            return (double) data.get("rating");
        }
        else {
            return (long) data.get("rating");
        }
    }

    public long getAgreements() {
        return (long) data.get("agree");
    }

    public long getDisagreements() {
        if (data.get("disagree") != null) {
            return (long) data.get("disagree");
        } else {
            return 0;
        }
    }

    public String getText() {
        return (String) data.get("body");
    }

    public long getCreatedAt() {
        return (long) data.get("created_at");
    }

    public long getUpdatedAt() {
        return (long) data.get("updated_at");
    }

    public long getEditedAt() {
        if (data.get("edited_at") != null) {
            return  (long) data.get("edited_at");
        } else {
            return getUpdatedAt();
        }
    }

}
