package mk.ukim.finki.emt.accomodation.service;

import mk.ukim.finki.emt.accomodation.service.forms.AccommodationForm;
import mk.ukim.finki.emt.accomodation.domain.models.Accommodation;
import mk.ukim.finki.emt.accomodation.domain.models.PlaceId;

import java.util.List;
import java.util.Optional;

public interface AccommodationService {
    Optional<Accommodation> save(AccommodationForm a);

    void deleteById(String id);

    List<Accommodation> findAll();

    Optional<Accommodation> findById(String of);
    List<AccommodationForm> findAllByPlace(PlaceId id);
    List<Accommodation> findByPlace(PlaceId id);
}
