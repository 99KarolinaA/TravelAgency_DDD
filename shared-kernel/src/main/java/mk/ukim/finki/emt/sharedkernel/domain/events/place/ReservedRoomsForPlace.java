package mk.ukim.finki.emt.sharedkernel.domain.events.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.events.DomainEvent;
import mk.ukim.finki.emt.sharedkernel.domain.events.topics.TopicHolder;

import java.time.LocalDateTime;

@Getter
public class ReservedRoomsForPlace extends DomainEvent {

    private String id;
    private Integer qty;

    public ReservedRoomsForPlace(@JsonProperty("id") String id,
                                 @JsonProperty("qty") int qty) {
        super(TopicHolder.TOPIC_RESERVE_ROOMS, LocalDateTime.now());
        this.id = id;
        this.qty = qty;
    }

}
