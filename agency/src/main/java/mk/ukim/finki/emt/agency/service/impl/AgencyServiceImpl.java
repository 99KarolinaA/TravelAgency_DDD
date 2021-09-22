package mk.ukim.finki.emt.agency.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.agency.domain.repository.AgencyRepository;
import mk.ukim.finki.emt.agency.domain.model.Agency;
import mk.ukim.finki.emt.agency.domain.model.AgencyId;
import mk.ukim.finki.emt.agency.service.AgencyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AgencyServiceImpl implements AgencyService {
    private final AgencyRepository agencyRepository;

    /*
        When an offer is reserved, the agency needs to update the values of the variables numOffersReserved and totalOffersPrice
    */
    @Override
    public void reserveOffer(AgencyId id, double price) {
        Agency a = agencyRepository.getById(id);
        a.addReservedOffer();
        a.increaseOffersPrice(price);
    }
}
