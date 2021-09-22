package mk.ukim.finki.emt.offermanagement.domain.repository;

import mk.ukim.finki.emt.offermanagement.domain.models.Offer;
import mk.ukim.finki.emt.offermanagement.domain.models.OfferId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, OfferId> {
}
