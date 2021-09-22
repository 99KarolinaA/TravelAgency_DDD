package mk.ukim.finki.emt.offermanagement.domain.models;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class ReservationId extends DomainObjectId {
    private ReservationId() {
        super(ReservationId.randomId(ReservationId.class).getId());

    }
    public ReservationId(@NonNull String uuid){
        super(uuid);
    }
}
