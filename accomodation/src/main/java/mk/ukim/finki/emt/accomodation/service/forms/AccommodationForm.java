package mk.ukim.finki.emt.accomodation.service.forms;

import lombok.Data;
import lombok.Getter;


/*
    This form is used for the frontend for creating new accommodation
    and also when the offer bounded context makes request to the accommodation bounded context
    for calculating the offerPrice
 */
@Data
@Getter
public class AccommodationForm {
    private String name;
    private String address;
    private String type;
    private Long pricePerPerson;
    private String placeId;

    public AccommodationForm(String name, String address, String type, Long pricePerPerson, String placeId) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.pricePerPerson = pricePerPerson;
        this.placeId = placeId;
    }
}
