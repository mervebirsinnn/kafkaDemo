package org.example;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.transaction.annotation.Transactional;
@Service

public class PaymentConsumer {

    private int balance = 1000;
    private final Set<String> processEventIds = new HashSet<>();

    @Transactional
     public void consume(PaymentEvent event){
         if(processEventIds.contains(event.getEventId())) {
             System.out.println(event.getEventId());
             return;
         }
         balance -= event.getAmount();
         System.out.println(
                 "Event:" + event.getEventId()
                         + " | Kalan bakiye:" + balance
         );

         processEventIds.add(event.getEventId());

         System.out.println(
                 "Event:" + event.getEventId()
                 + " | Kalan bakiye:" + balance
         );
     }


}
