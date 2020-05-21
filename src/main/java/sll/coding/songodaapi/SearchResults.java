package sll.coding.songodaapi;

import org.json.simple.JSONObject;

public class SearchResults {

    private final JSONObject links;
    private final JSONObject meta;

    public SearchResults(JSONObject links, JSONObject meta) {
        this.links = links;
        this.meta = meta;
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

}
