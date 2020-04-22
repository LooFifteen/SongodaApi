package sll.coding.songodaapi;

public enum JarStatus {

    STABLE("Stable"),
    ALPHA("Alpha"),
    BETA("Beta");

    private final String string;

    JarStatus(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

}
