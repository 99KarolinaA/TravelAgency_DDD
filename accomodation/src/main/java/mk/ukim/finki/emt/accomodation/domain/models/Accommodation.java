package mk.ukim.finki.emt.accomodation.domain.models;

import lombok.Getter;
import mk.ukim.finki.emt.accomodation.domain.enumerations.AccommodationType;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Money;

import javax.persistence.*;

@Entity
@Table(name = "accommodation")
@Getter
public class Accommodation extends AbstractEntity<AccommodationId> {
    private String name;
    private String address;
    @Enumerated(value = EnumType.STRING)
    private AccommodationType type;
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "accommodationPrice_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "accommodationPrice_currency"))
    })
    private Money pricePerPerson;
    @ManyToOne
    /*    @JsonIgnore*/
    private Place place;

    public Accommodation(String name, String address, AccommodationType type, Money pricePerPerson, Place place) {
        super(AccommodationId.randomId(AccommodationId.class));
        this.name = name;
        this.address = address;
        this.type = type;
        this.pricePerPerson = pricePerPerson;
        this.place = place;

    }

    public Accommodation() {
        super(AccommodationId.randomId(AccommodationId.class));
    }

}
