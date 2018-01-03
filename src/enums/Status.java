package enums;

public enum Status {
    OrderReceived("Order received"),
    PaymentReceived("Payment received"),
    CollectingOrder("Collecting order"),
    PackingOrder("Packing order"),
    ShippingOrder("Shipping order"),
    AwaitingPayment("Awaiting payment");

    // Member to hold the name
    private String string;

    // constructor to set the string
    Status(String name){string = name;}

    public static Status fromString(String text) {
        for (Status s : Status.values()) {
            if (s.string.equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

    @Override
    public String toString() {
        return string;
    }
}
