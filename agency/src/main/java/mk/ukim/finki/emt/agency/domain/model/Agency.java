package mk.ukim.finki.emt.agency.domain.model;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Money;

import javax.persistence.*;


@Entity
@Getter
@Table(name = "agencies")
public class Agency extends AbstractEntity<AgencyId> {
    private String name;
    private String phone;
    private String address;

    // After every reservation of an offer -> numOffersReserved++, this variable is used in the statistics for the agency
    private Integer numOffersReserved;

    /*
         After every reservation of an offer this variable increases its value by the totalPrice of the reservation,
         also used for statistics
    */
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "totalOffers_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "totalOffers_currency"))
    })
    private Money totalOffersPrice;

    public void addReservedOffer() {
        numOffersReserved++;
    }

    public void removeReservedOffer() {
        numOffersReserved--;
    }

    public void increaseOffersPrice(double totalPriceReservation) {
        totalOffersPrice = new Money(Currency.MKD, totalOffersPrice.getAmount() + totalPriceReservation);
    }
}
