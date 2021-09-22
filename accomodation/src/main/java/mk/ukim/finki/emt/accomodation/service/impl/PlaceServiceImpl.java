package mk.ukim.finki.emt.accomodation.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.accomodation.service.PlaceService;
import mk.ukim.finki.emt.accomodation.service.forms.PlaceForm;
import mk.ukim.finki.emt.accomodation.domain.models.Place;
import mk.ukim.finki.emt.accomodation.domain.models.PlaceId;
import mk.ukim.finki.emt.accomodation.domain.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    // When an offer is reserved, the variable numRoomsAgency needs to be decreased
    @Override
    public Place reservePlace(PlaceId placeId, int quantity) {
       Place place = placeRepository.findById(placeId).get();
       place.reserveRooms(quantity);
       placeRepository.saveAndFlush(place);
       return place;
    }

    @Override
    public Optional<Place> save(PlaceForm p) {
        Place place = new Place(p.getName(), p.getNumRoomsAgency());
        return Optional.of(placeRepository.save(place));
    }

    @Override
    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        placeRepository.deleteById(PlaceId.of(id));
    }

    @Override
    public Optional<Place> findById(String id) {
        return placeRepository.findById(PlaceId.of(id));
    }
}
