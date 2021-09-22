package mk.ukim.finki.emt.sharedkernel.domain.events.agency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.topics.TopicHolder;

import java.time.LocalDateTime;

@Getter
public class ReservedOffersForAgency extends DomainEvent {

    private String id;
    private double totalOffersPrice;

    public ReservedOffersForAgency(@JsonProperty("id") String id,
                                   @JsonProperty("totalOffersPrice") double totalOffersPrice) {
        super(TopicHolder.TOPIC_RESERVE_OFFERS, LocalDateTime.now());
        this.id = id;
        this.totalOffersPrice = totalOffersPrice;
    }

}