package mk.ukim.finki.emt.offermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

/*
    This class is used when the offer bounded context makes request to the accommodation bounded context
    for calculating the offerPrice
 */
@Getter
public class Accommodation implements ValueObject {
    private final String name;
    private final String address;
    private final String type;
    private final Long pricePerPerson;
    private final String placeId;

    @JsonCreator
    public Accommodation(@JsonProperty("name") String name,
                         @JsonProperty("address") String address,
                         @JsonProperty("type") String type,
                         @JsonProperty("pricePerPerson") Long pricePerPerson,
                         @JsonProperty("placeId") String placeId) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.pricePerPerson = pricePerPerson;
        this.placeId = placeId;
    }
}
