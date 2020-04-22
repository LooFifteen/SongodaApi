package sll.coding.songodaapi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Jar {

    private final JSONObject data;

    public Jar(JSONObject data) {
        this.data = data;
    }

    public long getId() {
        return (long) data.get("id");
    }

    public JarStatus getStatus() {
        String status = (String) data.get("status");
        if (status.equalsIgnoreCase("beta")) return JarStatus.BETA;
        else if (status.equalsIgnoreCase("alpha")) return JarStatus.ALPHA;
        else return JarStatus.STABLE;
    }

    public String getVersion() {
        return (String) data.get("version");
    }

    public String getSize() {
        return (String) data.get("size");
    }

    public long getCreatedAt() {
        return (long) data.get("created_at");
    }

    public User getUploadedBy() {
        String name = (String) data.get("uploaded_by");
        for (User user : User.fromName(name)) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public List<String> getMinecraftVersions() {
        List<String> minecraftVersions = new ArrayList<>();
        for (Object v : (JSONArray) data.get("minecraft_version")) {
            minecraftVersions.add((String) v);
        }
        return minecraftVersions;
    }

    public String getUrl() {
        return (String) data.get("url");
    }

    public long getDownloads() {
        return (long) data.get("downloads");
    }

    public String getDownload() {
        return (String) data.get("download");
    }

}
