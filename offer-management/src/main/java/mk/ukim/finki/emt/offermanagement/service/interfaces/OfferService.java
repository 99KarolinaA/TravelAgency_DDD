package mk.ukim.finki.emt.offermanagement.service.interfaces;

import mk.ukim.finki.emt.offermanagement.domain.exceptions.OfferIdNotExistsException;
import mk.ukim.finki.emt.offermanagement.domain.exceptions.ReservationIdNotExistsException;
import mk.ukim.finki.emt.offermanagement.domain.models.Offer;
import mk.ukim.finki.emt.offermanagement.domain.models.OfferId;
import mk.ukim.finki.emt.offermanagement.domain.models.ReservationId;
import mk.ukim.finki.emt.offermanagement.service.forms.OfferForm;
import mk.ukim.finki.emt.offermanagement.service.forms.ReservationForm;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    Optional<Offer> createOffer(OfferForm offerForm);
    void calculatePriceOffer(OfferId offerId, double price);

    List<Offer> findAll();
    List<Offer> findAllWithoutTransportation();

    Optional<Offer> findById(OfferId id);

    void makeReservationForOffer(ReservationForm reservationForm)
            throws OfferIdNotExistsException;

    void deleteReservationForOffer(OfferId offerId,
                                   ReservationId reservationId)
            throws OfferIdNotExistsException, ReservationIdNotExistsException;
    List <Offer> chooseOfferToReservate(String typeRoom, double amountRooms);

    void deleteById(OfferId id);
    Optional <Offer> edit(OfferForm offerForm, OfferId offerId);
    void addTransportationToOffer(String transportationId, String offerId);

}
