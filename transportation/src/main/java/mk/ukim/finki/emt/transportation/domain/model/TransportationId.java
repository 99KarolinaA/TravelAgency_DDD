package mk.ukim.finki.emt.transportation.domain.model;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class TransportationId extends DomainObjectId {
    private TransportationId() {
        super(TransportationId.randomId(TransportationId.class).getId());
    }

    public TransportationId(@NonNull String uuid) {
        super(uuid);
    }

    public static TransportationId of(String uuid) {
        TransportationId p = new TransportationId(uuid);
        return p;
    }
}
