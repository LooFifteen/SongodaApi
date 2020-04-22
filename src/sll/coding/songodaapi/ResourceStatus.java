package sll.coding.songodaapi;

public enum ResourceStatus {

    APPROVED("Approved"),
    PENDING("Pending"),
    DECLINED("Declined");

    private final String string;

    ResourceStatus(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

}
