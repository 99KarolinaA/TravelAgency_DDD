package mk.ukim.finki.emt.accomodation.domain.repository;

import mk.ukim.finki.emt.accomodation.domain.models.Accommodation;
import mk.ukim.finki.emt.accomodation.domain.models.AccommodationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, AccommodationId> {
}
