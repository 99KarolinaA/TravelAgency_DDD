package mk.ukim.finki.emt.offermanagement.domain.valueobjects;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class AgencyId extends DomainObjectId {
    private AgencyId() {
        super(AgencyId.randomId(AgencyId.class).getId());
    }
    public AgencyId(String uuid) {
        super(uuid);
    }
}
