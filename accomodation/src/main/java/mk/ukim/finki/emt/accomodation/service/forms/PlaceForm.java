package mk.ukim.finki.emt.accomodation.service.forms;

import lombok.Data;
import lombok.Getter;

/*
    This form is used for the frontend for creating new place
 */
@Data
@Getter
public class PlaceForm {
    private String name;
    private Integer numRoomsAgency;

    public PlaceForm(String name, Integer numRoomsAgency) {
        this.name = name;
        this.numRoomsAgency = numRoomsAgency;
    }
}
