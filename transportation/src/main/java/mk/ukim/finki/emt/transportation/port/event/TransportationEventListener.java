package mk.ukim.finki.emt.transportation.port.event;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.topics.TopicHolder;
import mk.ukim.finki.emt.sharedkernel.domain.events.transportation.ReservedSeatsForTransportation;
import mk.ukim.finki.emt.transportation.domain.model.TransportationId;
import mk.ukim.finki.emt.transportation.service.TransportationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransportationEventListener {

    private final TransportationService transportationService;

    /*
    When an offer is reserved, the transportation needs to update the variable numSeatsAgency
    Transportation and Offer are two different bounded contexts so they communicate using domain events
    */
    @KafkaListener(topics = TopicHolder.TOPIC_RESERVE_SEATS, groupId = "agency")
    public void reserveSeatsEvent(String jsonMessage) {
        try {
            ReservedSeatsForTransportation event = DomainEvent.fromJson(jsonMessage, ReservedSeatsForTransportation.class);
            transportationService.reserveSeats(TransportationId.of(event.getId()), event.getQty());
        } catch (Exception e) {

        }

    }
}
