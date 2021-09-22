package mk.ukim.finki.emt.offermanagement.domain.models;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class OfferId extends DomainObjectId {
    private OfferId() {
        super(OfferId.randomId(OfferId.class).getId());
    }
    public OfferId(String uuid) {
        super(uuid);
    }
    public static OfferId of(String uuid) {
        OfferId o = new OfferId(uuid);
        return o;
    }
}
