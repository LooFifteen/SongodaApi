package sll.coding.songodaapi;

public enum PaymentMethod {

    PATREON("Patreon"),
    PAYPAL("PayPal"),
    UNKNOWN("Unknown");

    private final String string;

    PaymentMethod(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

}
