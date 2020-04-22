package sll.coding.songodaapi;

public enum OwnerType {

    USER("User"),
    TEAM("Team");

    private String string;

    OwnerType(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
