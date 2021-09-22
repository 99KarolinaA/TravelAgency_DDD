package mk.ukim.finki.emt.offermanagement.service.forms;

import lombok.Data;

/*
    This form is used for the frontend for reserving an offer
 */
@Data
public class ReservationForm {
    private String typeRoom;
    private double amount;
    private double pricePerRoom;
    private String offerId;

    public ReservationForm(String typeRoom,
                           double amount, double pricePerRoom, String offerId) {
        this.typeRoom = typeRoom;
        this.amount = amount;
        this.pricePerRoom = pricePerRoom;
        this.offerId = offerId;
    }
}
