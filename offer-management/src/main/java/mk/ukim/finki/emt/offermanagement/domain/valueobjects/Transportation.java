package mk.ukim.finki.emt.offermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

/*
    This class is used when the offer bounded context makes request to the transportation bounded context
    for calculating the offerPrice
 */
@Getter
public class Transportation implements ValueObject
{
    private final String id;
    private final String name;
    private final Integer numSeatsAgency;
    private final Long fare;
    private final Integer numVehicles;
    private final String type;

    @JsonCreator
    public Transportation(@JsonProperty("id") String id,
                          @JsonProperty("name") String name,
                          @JsonProperty("numSeatsAgency") Integer numSeatsAgency,
                          @JsonProperty("fare") Long fare,
                          @JsonProperty("numVehicles") Integer numVehicles,
                          @JsonProperty("type") String type
    ) {
        this.id=id;
        this.name = name;
        this.numSeatsAgency = numSeatsAgency;
        this.fare = fare;
        this.numVehicles = numVehicles;
        this.type = type;
    }
}
