package mk.ukim.finki.emt.accomodation.domain.models;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class PlaceId extends DomainObjectId {

    private PlaceId() {
        super(PlaceId.randomId(PlaceId.class).getId());
    }
    public PlaceId(@NonNull String uuid) {
        super(uuid);
    }
    public static PlaceId of(String uuid) {
        PlaceId p = new PlaceId(uuid);
        return p;
    }
}
