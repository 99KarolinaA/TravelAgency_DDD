package mk.ukim.finki.emt.offermanagement.domain.valueobjects;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;


@Getter
public class Agency implements ValueObject {
    private final AgencyId id;

    public Agency() {
        this.id = AgencyId.randomId(AgencyId.class);

    }

}
