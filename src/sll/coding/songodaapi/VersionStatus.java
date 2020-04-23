package sll.coding.songodaapi;

public enum VersionStatus {

    STABLE("Stable"),
    ALPHA("Alpha"),
    BETA("Beta");

    private final String string;

    VersionStatus(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

}
