package mk.ukim.finki.emt.transportation.domain.repository;

import mk.ukim.finki.emt.transportation.domain.model.Transportation;
import mk.ukim.finki.emt.transportation.domain.model.TransportationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportationRepository extends JpaRepository<Transportation, TransportationId>
{
}
