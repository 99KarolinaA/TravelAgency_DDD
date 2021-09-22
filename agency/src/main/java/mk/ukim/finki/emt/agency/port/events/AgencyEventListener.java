package mk.ukim.finki.emt.agency.port.events;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.agency.domain.model.AgencyId;
import mk.ukim.finki.emt.agency.service.AgencyService;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.agency.ReservedOffersForAgency;
import mk.ukim.finki.emt.sharedkernel.domain.events.topics.TopicHolder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgencyEventListener {

    private final AgencyService agencyService;

    /*
        When an offer is reserved, the agency needs to update the values of the variables numOffersReserved and totalOffersPrice
        Agency and Offer are two different bounded contexts so they communicate using domain events
    */
    @KafkaListener(topics = TopicHolder.TOPIC_RESERVE_OFFERS, groupId = "agency")
    public void reserveOfferAgencyEvent(String jsonMessage) {
        try {
            ReservedOffersForAgency event = DomainEvent.fromJson(jsonMessage, ReservedOffersForAgency.class);
            agencyService.reserveOffer(AgencyId.of(event.getId()), event.getTotalOffersPrice());
        } catch (Exception e) {

        }

    }
}