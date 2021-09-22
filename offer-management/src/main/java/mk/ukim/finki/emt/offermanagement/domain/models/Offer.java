package mk.ukim.finki.emt.offermanagement.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.emt.offermanagement.domain.valueobjects.AgencyId;
import mk.ukim.finki.emt.offermanagement.domain.valueobjects.PlaceId;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Room;
import mk.ukim.finki.emt.offermanagement.domain.valueobjects.TransportationId;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.TypeRoom;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Money;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Table(name = "offers")
@Setter
/*
   AGGREGATE ROOT OF THE OFFER-MANAGEMENT BOUNDED CONTEXT
 */
public class Offer extends AbstractEntity<OfferId> {

    private String nameOffer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateTo;
    private Integer numSeats;
    // After reservation of an offer -> numReservations++
    private Integer numReservations;
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "priceOffer_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "priceOffer_currency"))
    })
    //  Accommodation price + fare, example of its calculation -> OfferServiceImplTest
    private Money priceOfferPerPerson;

    // sum of the variables totalPrice in each Reservation
    public Money totalReservationsPrice() {
        return reservationSet.stream().map(r -> r.ReservationPrice(this.getPriceOfferPerPerson().getAmount()))
                .reduce(new Money(Currency.MKD, 0), Money::add);
    }

    @AttributeOverride(name = "id", column = @Column(name = "transportation_id"))
    private TransportationId transportationId;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "offer")
    @JsonIgnore
    private Set<Reservation> reservationSet = new HashSet<>();
    @AttributeOverride(name = "rooms", column = @Column(name = "offer_rooms"))

    private Room rooms;
    private PlaceId placeId;
    private AgencyId agencyId;

    public Offer(String nameOffer, LocalDateTime dateFrom, LocalDateTime dateTo,
                 Integer numSeats, Integer numReservations, double amount, String typeRoom, String currencyRoom,
                 double priceRoom, String placeId, String transportationId) {
        super(OfferId.randomId(OfferId.class));
        this.numReservations = 0;
        this.nameOffer = nameOffer;
        var pricePerRoom = new Money(Currency.valueOf(currencyRoom), priceRoom);
        var room = new Room(TypeRoom.valueOf(typeRoom), amount, pricePerRoom);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.numSeats = numSeats;
        this.numReservations = numReservations;
        this.rooms = room;
        if (placeId != null)
            this.placeId = PlaceId.of(placeId);
        if (transportationId != null) {
            this.transportationId = TransportationId.of(transportationId);
        }
    }


    public Offer() {

    }

    public Reservation makeReservation(String typeRoom, double amount
            , double priceRoom, String currencyPriceRoom) {
        var price = new Money(Currency.valueOf(currencyPriceRoom), priceRoom);
        var room = new Room(TypeRoom.valueOf(typeRoom), amount, price);
        var newReservation = new Reservation(LocalDateTime.now(),
                room);
        newReservation.setOffer(this);
        /*
            Hard coded value set for priceOfferPerPerson in order to calculate the variable totalPrice in Reservation
            To calculate the real priceOfferPerPerson, requests must be send to the both bounded contexts (Accommodation and transport)
            Example of its calculation -> OfferServiceImplTest
         */
        newReservation.ReservationPrice(1000);
        numReservations++;
        reservationSet.add(newReservation);
        return newReservation;
    }

    public void removeReservation(@NonNull ReservationId reservationId) {
        Objects.requireNonNull(reservationId, "reservation id must not be null");
        reservationSet.removeIf(r -> r.getId().equals(reservationId));
    }

}
