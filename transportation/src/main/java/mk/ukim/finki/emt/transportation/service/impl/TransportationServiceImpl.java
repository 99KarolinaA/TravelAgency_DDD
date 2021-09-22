package mk.ukim.finki.emt.transportation.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Money;
import mk.ukim.finki.emt.transportation.service.TransportationService;
import mk.ukim.finki.emt.transportation.service.forms.TransportationForm;
import mk.ukim.finki.emt.transportation.domain.enumerations.TransportType;
import mk.ukim.finki.emt.transportation.domain.model.Transportation;
import mk.ukim.finki.emt.transportation.domain.model.TransportationId;
import mk.ukim.finki.emt.transportation.domain.repository.TransportationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TransportationServiceImpl implements TransportationService {

    private final TransportationRepository transportationRepository;

    @Override
    public List<Transportation> findAll() {
        return transportationRepository.findAll();
    }

    // This method is used to calculate PriceOffer in the offer bounded context (accommodationPrice + transportationFare)
    public List<TransportationForm> findAllForms() {
        List<Transportation> transportationList = transportationRepository.findAll();
        List<TransportationForm> transportationForms = new ArrayList<>();
        for (Transportation t : transportationList) {
            TransportationForm transportationForm = new TransportationForm(
                    t.getId().getId(),
                    t.getName(), t.getNumSeatsAgency(), (long) t.getFarePerPerson().getAmount(), t.getNumVehicles(),
                    t.getType().toString(), t.getOfferId());
            transportationForms.add(transportationForm);
        }
        return transportationForms;
    }

    // When an offer is reserved, the variable numSeatsAgency needs to be decreased
    @Override
    public void reserveSeats(TransportationId transportationId, Integer qty) {
        Transportation transportation = transportationRepository.findById(transportationId).get();
        transportation.reserveSeats(qty);
        transportationRepository.saveAndFlush(transportation);
    }


    @Override
    public TransportationForm findById(String id) {
        Transportation t = transportationRepository.findById(TransportationId.of(id)).get();
        TransportationForm transportationForm = new TransportationForm(t.getId().getId(),
                t.getName(), t.getNumSeatsAgency(),
                (long) (t.getFarePerPerson().getAmount()),
                t.getNumVehicles(),
                t.getType().toString(), t.getOfferId());
        return transportationForm;
    }

    @Override
    public Optional<Transportation> save(TransportationForm t) {
        TransportType transportType = TransportType.valueOf(t.getType());
        Money money = new Money(Currency.MKD, t.getFare());
        Transportation transportation = new Transportation(t.getName(), t.getNumSeatsAgency(), money, t.getNumVehicles(), transportType,
                t.getOfferId());
        return Optional.of(transportationRepository.save(transportation));
    }

    @Override
    public void deleteById(String id) {
        transportationRepository.deleteById(TransportationId.of(id));
    }
}
