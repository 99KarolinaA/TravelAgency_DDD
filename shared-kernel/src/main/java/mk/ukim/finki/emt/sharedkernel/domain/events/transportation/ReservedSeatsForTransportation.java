package mk.ukim.finki.emt.sharedkernel.domain.events.transportation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.topics.TopicHolder;

import java.time.LocalDateTime;

@Getter
public class ReservedSeatsForTransportation extends DomainEvent {
    private String id;
    private Integer qty;

    public ReservedSeatsForTransportation(@JsonProperty("id") String id,
                                          @JsonProperty("qty") int qty) {
        super(TopicHolder.TOPIC_RESERVE_SEATS, LocalDateTime.now());
        this.id = id;
        this.qty = qty;
    }
}
