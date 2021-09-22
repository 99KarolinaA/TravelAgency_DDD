package mk.ukim.finki.emt.offermanagement.domain.valueobjects;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

/*
    Relation between the accommodation bounded context and the offer-management bounded context
 */
public class PlaceId extends DomainObjectId {
    private PlaceId() {
        super(PlaceId.randomId(PlaceId.class).getId());
    }
    public PlaceId(String uuid) {
        super(uuid);
    }
    public static PlaceId of(String uuid) {
        PlaceId p = new PlaceId(uuid);
        return p;
    }
}
