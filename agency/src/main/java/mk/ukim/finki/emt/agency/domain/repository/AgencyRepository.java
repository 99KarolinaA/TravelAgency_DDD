package mk.ukim.finki.emt.agency.domain.repository;

import mk.ukim.finki.emt.agency.domain.model.Agency;
import mk.ukim.finki.emt.agency.domain.model.AgencyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, AgencyId> {
}
