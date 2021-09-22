package mk.ukim.finki.emt.accomodation.domain.repository;

import mk.ukim.finki.emt.accomodation.domain.models.Place;
import mk.ukim.finki.emt.accomodation.domain.models.PlaceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, PlaceId> {
}
