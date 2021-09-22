package mk.ukim.finki.emt.sharedkernel.domain.valueObjects;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.TypeRoom;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.Currency;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
public class Room implements ValueObject {
    @Enumerated(value = EnumType.STRING)
    private final TypeRoom typeRoom;

    private final double amountRoom; //how much two-beds rooms/three-beds
    private final Money pricePerRoom;

    public Room(@NonNull TypeRoom typeRoom,
                double amount,
                @NonNull Money pricePerRoom) {
        this.typeRoom = typeRoom;
        this.amountRoom = amount;
        this.pricePerRoom = pricePerRoom;
    }

    protected Room() {
        this.amountRoom = 0.0;
        this.typeRoom = null;
        this.pricePerRoom = null;
    }

    public Room reduceAmountRooms(int qty) {
        return new Room(typeRoom, amountRoom - qty, pricePerRoom);
    }

    public Money totalPrice() {
        return new Money(Currency.MKD, amountRoom * pricePerRoom.getAmount());
    }

}
