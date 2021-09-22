package mk.ukim.finki.emt.accomodation.service;

import mk.ukim.finki.emt.accomodation.service.forms.PlaceForm;
import mk.ukim.finki.emt.accomodation.domain.models.Place;
import mk.ukim.finki.emt.accomodation.domain.models.PlaceId;

import java.util.List;
import java.util.Optional;

public interface PlaceService {
    Place reservePlace(PlaceId placeId,int quantity);
    Optional<Place> save(PlaceForm p);
    List<Place> findAll();
    void deleteById(String id);

    Optional<Place> findById(String id);
}
