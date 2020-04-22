package sll.coding.songodaapi;

public enum ResourceType {

    PLUGIN("Plugin"),
    CONFIG("Config"),
    SKRIPT("Skript"),
    SCHEMATIC("Schematic"),
    BUILD("Build"),
    WEBSITE("Website"),
    LIBRARY("Library / API"),
    UNKNOWN("Unknown");

    private String string;

    ResourceType(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
