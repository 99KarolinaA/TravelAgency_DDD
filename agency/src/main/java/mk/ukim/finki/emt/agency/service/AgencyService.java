package mk.ukim.finki.emt.agency.service;

import mk.ukim.finki.emt.agency.domain.model.AgencyId;

public interface AgencyService {

    void reserveOffer(AgencyId id, double qty);
}
