package sll.coding.songodaapi;

public enum JarStatus {

    STABLE("Stable"),
    ALPHA("Alpha"),
    BETA("Beta");

    private String string;

    JarStatus(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
