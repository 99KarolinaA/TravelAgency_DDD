package mk.ukim.finki.emt.accomodation.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import mk.ukim.finki.emt.accomodation.domain.exceptions.NotEnoughAvailableRooms;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "place")
@Getter
/*
   AGGREGATE ROOT OF THE ACCOMMODATION BOUNDED CONTEXT
 */
public class Place extends AbstractEntity<PlaceId> {
    private String name;
    private Integer numRoomsAgency;
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER, mappedBy = "place")
    @JsonIgnore
    private List<Accommodation> accommodationSet;


    // When an offer is reserved, the variable numRoomsAgency needs to be decreased
    public void reserveRooms(int qty) throws NotEnoughAvailableRooms {
        int available = numRoomsAgency - qty;
        if (available < 0)
            throw new NotEnoughAvailableRooms();
        else {
            numRoomsAgency -= qty;
        }
    }

    public Place() {
        super(PlaceId.randomId(PlaceId.class));
    }

    public Place(String name, Integer numRooms) {
        super(PlaceId.randomId(PlaceId.class));
        this.name = name;
        this.numRoomsAgency = numRooms;
    }
}
