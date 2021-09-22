package mk.ukim.finki.emt.accomodation.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.accomodation.domain.enumerations.AccommodationType;
import mk.ukim.finki.emt.accomodation.service.AccommodationService;
import mk.ukim.finki.emt.accomodation.service.forms.AccommodationForm;
import mk.ukim.finki.emt.accomodation.domain.models.*;
import mk.ukim.finki.emt.accomodation.domain.repository.AccommodationRepository;
import mk.ukim.finki.emt.accomodation.domain.repository.PlaceRepository;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {
    private AccommodationRepository repository;
    private PlaceRepository placeRepository;

    @Override
    public Optional<Accommodation> save(AccommodationForm a) {
        AccommodationType accommodationType = AccommodationType.valueOf(a.getType());
        Money money = new Money(Currency.MKD, a.getPricePerPerson());
        PlaceId placeId = PlaceId.of(a.getPlaceId());
        Place place = placeRepository.findById(placeId).get();
        Accommodation accommodation = new Accommodation(a.getName(), a.getAddress(), accommodationType, money, place);
        return Optional.of(repository.save(accommodation));
    }

    @Override
    public void deleteById(String id) {
        Accommodation a = this.findById(id).get();
        a.getPlace().getAccommodationSet().removeIf(a1 ->
                a1.getId().getId().equals(id));
        repository.deleteById(AccommodationId.of(id));
        a.getPlace();

    }

    @Override
    public List<Accommodation> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Accommodation> findById(String id) {
        return repository.findById(AccommodationId.of(id));
    }

    // This method is used to calculate PriceOffer in the offer bounded context (accommodationPrice + transportationFare)
    @Override
    public List<AccommodationForm> findAllByPlace(PlaceId id) {
        Place place = placeRepository.findById(id).get();
        List <AccommodationForm> list = new ArrayList<>();
        for(int i=0;i<place.getAccommodationSet().size();i++){
            Accommodation a1 = place.getAccommodationSet().get(i);
            AccommodationForm form = new AccommodationForm(a1.getName(), a1.getAddress(),
                    a1.getType().toString(),(long)a1.getPricePerPerson().getAmount(),a1.getPlace().getId().getId());
            list.add(form);
        }
        return list;
    }

    @Override
    public List<Accommodation> findByPlace(PlaceId id) {
        Place place = placeRepository.findById(id).get();
        return place.getAccommodationSet();
    }
}
