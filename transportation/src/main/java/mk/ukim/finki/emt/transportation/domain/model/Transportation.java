package mk.ukim.finki.emt.transportation.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Money;
import mk.ukim.finki.emt.transportation.domain.enumerations.TransportType;
import mk.ukim.finki.emt.transportation.domain.exceptions.NotEnoughAvailableSeats;

import javax.persistence.*;

@Entity
@Table(name = "transportation")
@Getter
public class Transportation extends AbstractEntity<TransportationId> {
    private String name;
    private Integer numSeatsAgency;
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "fare_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "fare_currency"))
    })
    private Money farePerPerson;
    private Integer numVehicles;
    @Enumerated(value = EnumType.STRING)
    private TransportType type;
    private String offerId;

    public Transportation(String name, Integer numSeatsAgency, Money fare, Integer numVehicles, TransportType type,
                          String offerId) {
        super(TransportationId.randomId(TransportationId.class));
        this.name = name;
        this.numSeatsAgency = numSeatsAgency;
        this.farePerPerson = fare;
        this.numVehicles = numVehicles;
        this.type = type;
        this.offerId = offerId;

    }

    public Transportation() {

    }

    // When an offer is reserved, the variable numSeatsAgency needs to be decreased
    public void reserveSeats(int quantity) {
        int available = numSeatsAgency - quantity;
        if (available < 0)
            throw new NotEnoughAvailableSeats();
        else {
            numSeatsAgency -= quantity;
        }
    }


}
