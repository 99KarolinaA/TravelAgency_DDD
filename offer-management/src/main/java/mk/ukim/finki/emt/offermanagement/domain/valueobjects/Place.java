package mk.ukim.finki.emt.offermanagement.domain.valueobjects;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

/*
    This class is used when the offer bounded context makes request to the accommodation bounded context
    for calculating the offerPrice
 */
@Getter
public class Place implements ValueObject {
    private final PlaceId id;
    private final String name;
    private final Integer numRoomsAgency;

    public Place() {
        this.id = PlaceId.randomId(PlaceId.class);
        this.name = "";
        this.numRoomsAgency = 0;

    }

    public Place(String name, Integer numRoomsAgency) {
        this.id=PlaceId.randomId(PlaceId.class);
        this.name = name;
        this.numRoomsAgency = numRoomsAgency;
    }
}
