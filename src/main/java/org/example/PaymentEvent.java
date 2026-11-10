package org.example;

public class PaymentEvent {

    private final String eventId;
    private final int amount;

    public PaymentEvent(String eventId, int amount) {
        this.eventId = eventId;
        this.amount = amount;
    }

    public String getEventId() {
        return eventId;
    }

    public int getAmount() {
        return amount;
    }
}