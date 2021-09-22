package mk.ukim.finki.emt.transportation.service.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

/*
    This form is used for the frontend for creating new transportation
    and also when the offer bounded context makes request to the transportation bounded context
    for calculating the offerPrice
 */
@Data
@Getter
public class TransportationForm {
    private String id;
    private String name;
    private Integer numSeatsAgency;
    private Long fare;
    private Integer numVehicles;
    private String type;
    private String offerId;

    @JsonCreator
    public TransportationForm(
            @JsonProperty("name") String name,
            Integer numSeatsAgency, Long fare, Integer numVehicles, String type
            , String offerId) {
        this.name = name;
        this.numSeatsAgency = numSeatsAgency;
        this.fare = fare;
        this.numVehicles = numVehicles;
        this.type = type;
        this.offerId = offerId;
    }

    public TransportationForm(String id, String name, Integer numSeatsAgency, Long fare, Integer numVehicles, String type, String offerId) {
        this.id = id;
        this.name = name;
        this.numSeatsAgency = numSeatsAgency;
        this.fare = fare;
        this.numVehicles = numVehicles;
        this.type = type;
        this.offerId = offerId;
    }
}
