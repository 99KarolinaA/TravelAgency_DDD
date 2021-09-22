package mk.ukim.finki.emt.offermanagement.service.forms;

import lombok.Data;
import lombok.Getter;

/*
    This form is used for the frontend for creating/editing new offer
 */
@Data
@Getter
public class OfferForm {
    private String nameOffer;
    private String dateFrom;
    private String dateTo;
    private Integer numSeats;
    private String transportationId;
    private String typeRoom;
    private double amount;
    private double priceRoom;
    private String placeId;

    public OfferForm(String nameOffer, String dateFrom, String dateTo,
                     Integer numSeats,
                     String transportationId, String typeRoom,
                     double amount, double priceRoom, String placeId) {
        this.nameOffer = nameOffer;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.numSeats = numSeats;
        this.transportationId = transportationId;
        this.typeRoom = typeRoom;
        this.amount = amount;
        this.priceRoom = priceRoom;
        this.placeId = placeId;
    }

}
