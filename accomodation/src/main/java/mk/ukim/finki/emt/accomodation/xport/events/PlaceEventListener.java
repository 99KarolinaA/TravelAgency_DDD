package mk.ukim.finki.emt.accomodation.xport.events;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.accomodation.domain.models.PlaceId;
import mk.ukim.finki.emt.accomodation.service.PlaceService;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.place.ReservedRoomsForPlace;
import mk.ukim.finki.emt.sharedkernel.domain.events.topics.TopicHolder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceEventListener {

    private final PlaceService placeService;

    /*
        When an offer is reserved, the accommodation context needs to update the variable numRoomsAgency in the Place entity
        Accommodation and Offer are two different bounded contexts so they communicate using domain events
    */
    @KafkaListener(topics = TopicHolder.TOPIC_RESERVE_ROOMS, groupId = "agency")
    public void reservePlaceEvent(String jsonMessage) {
        try {
            ReservedRoomsForPlace event = DomainEvent.fromJson(jsonMessage, ReservedRoomsForPlace.class);
            placeService.reservePlace(PlaceId.of(event.getId()), event.getQty());
        } catch (Exception e) {

        }

    }
}
