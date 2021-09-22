package mk.ukim.finki.emt.offermanagement.domain.valueobjects;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

/*
    Relation between the transportation bounded context and the offer-management bounded context
 */
@Embeddable
public class TransportationId extends DomainObjectId {
    private TransportationId() {
        super(TransportationId.randomId(TransportationId.class).getId());
    }
    public TransportationId(String uuid) {
        super(uuid);
    }

    public static TransportationId of(String uuid) {
        TransportationId p = new TransportationId(uuid);
        return p;
    }

}
