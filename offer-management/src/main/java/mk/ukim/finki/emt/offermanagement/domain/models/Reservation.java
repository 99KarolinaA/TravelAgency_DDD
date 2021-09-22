package mk.ukim.finki.emt.offermanagement.domain.models;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Room;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Money;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
public class Reservation extends AbstractEntity<ReservationId> {
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "reservation_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "reservation_currency"))
    })
    // calculated in the method below ReservationPrice
    private Money totalPrice;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateReservation;
    private Room rooms;
    @ManyToOne
    private Offer offer;

    public Reservation(Money totalPrice, LocalDateTime dateReservation, Room rooms) {
        super(DomainObjectId.randomId(ReservationId.class));
        this.totalPrice = totalPrice;
        this.dateReservation = dateReservation;
        this.rooms = rooms;
    }

    public Reservation(LocalDateTime dateReservation, Room rooms) {
        super(DomainObjectId.randomId(ReservationId.class));
        this.dateReservation = dateReservation;
        this.rooms = rooms;
    }

    public Money ReservationPrice(double priceOfferPerPerson) {
        Money totalPricePerReservation = new Money(Currency.MKD,
                (priceOfferPerPerson + this.getRooms().getPricePerRoom().getAmount())
                        * this.getRooms().getAmountRoom());
        this.setTotalPrice(totalPricePerReservation);
        return totalPricePerReservation;
    }

    public Reservation() {
        super(ReservationId.randomId(ReservationId.class));
    }
}
