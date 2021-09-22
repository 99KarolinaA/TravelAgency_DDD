package mk.ukim.finki.emt.agency.domain.model;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import org.springframework.lang.NonNull;

public class AgencyId extends DomainObjectId {
    private AgencyId() {
        super(AgencyId.randomId(AgencyId.class).getId());
    }

    public AgencyId(@NonNull String uuid) {
        super(uuid);
    }

    public static AgencyId of(String uuid) {
        AgencyId p = new AgencyId(uuid);
        return p;
    }
}
