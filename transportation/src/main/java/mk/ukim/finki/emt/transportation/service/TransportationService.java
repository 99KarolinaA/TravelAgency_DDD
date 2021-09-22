package mk.ukim.finki.emt.transportation.service;

import mk.ukim.finki.emt.transportation.service.forms.TransportationForm;
import mk.ukim.finki.emt.transportation.domain.model.Transportation;
import mk.ukim.finki.emt.transportation.domain.model.TransportationId;

import java.util.List;
import java.util.Optional;

public interface TransportationService {
    List<Transportation> findAll();

    TransportationForm findById(String id);

    Optional<Transportation> save(TransportationForm t);

    void deleteById(String id);

    List<TransportationForm> findAllForms();

    void reserveSeats(TransportationId of, Integer qty);
}
