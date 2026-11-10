package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        PaymentConsumer paymentConsumer = new PaymentConsumer();
        PaymentEvent    paymentEvent = new PaymentEvent("paymnet-1",100);
        paymentConsumer.consume(paymentEvent);
        paymentConsumer.consume(paymentEvent);
    }
}
