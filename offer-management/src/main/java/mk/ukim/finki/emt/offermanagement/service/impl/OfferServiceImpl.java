package mk.ukim.finki.emt.offermanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.offermanagement.domain.exceptions.NotValidReservationDateException;
import mk.ukim.finki.emt.offermanagement.domain.exceptions.OfferIdNotExistsException;
import mk.ukim.finki.emt.offermanagement.domain.exceptions.ReservationIdNotExistsException;
import mk.ukim.finki.emt.offermanagement.domain.exceptions.UnsuccessfulReservationException;
import mk.ukim.finki.emt.offermanagement.domain.models.Offer;
import mk.ukim.finki.emt.offermanagement.domain.models.OfferId;
import mk.ukim.finki.emt.offermanagement.domain.models.Reservation;
import mk.ukim.finki.emt.offermanagement.domain.models.ReservationId;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.TypeRoom;
import mk.ukim.finki.emt.offermanagement.domain.repository.OfferRepository;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Room;
import mk.ukim.finki.emt.offermanagement.domain.valueobjects.TransportationId;
import mk.ukim.finki.emt.offermanagement.service.forms.OfferForm;
import mk.ukim.finki.emt.offermanagement.service.forms.ReservationForm;
import mk.ukim.finki.emt.offermanagement.service.interfaces.OfferService;
import mk.ukim.finki.emt.sharedkernel.domain.events.agency.ReservedOffersForAgency;
import mk.ukim.finki.emt.sharedkernel.domain.events.place.ReservedRoomsForPlace;
import mk.ukim.finki.emt.sharedkernel.domain.events.transportation.ReservedSeatsForTransportation;
import mk.ukim.finki.emt.sharedkernel.domain.enumerations.Currency;
import mk.ukim.finki.emt.sharedkernel.domain.valueObjects.Money;
import mk.ukim.finki.emt.sharedkernel.infra.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final Validator validator;
    private final DomainEventPublisher domainEventPublisher;

    // Accommodation price + fare, example of its calculation -> OfferServiceImplTest
    public void calculatePriceOffer(OfferId offerId, double price) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferIdNotExistsException::new);
        offer.setPriceOfferPerPerson(new Money(Currency.MKD, price));
    }

    @Override
    public Optional<Offer> createOffer(OfferForm offerForm) {
        Objects.requireNonNull(offerForm, "offer must not be null!");
        var constraintViolations = validator.validate(offerForm);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The offer form is not valid", constraintViolations);
        }
        var newOrder = offerRepository.saveAndFlush(toDomainObject(offerForm));
        return Optional.of(newOrder);
    }

    private Offer toDomainObject(OfferForm offerForm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateFrom = LocalDate.parse(offerForm.getDateFrom(), formatter).atStartOfDay();
        LocalDateTime dateTo = LocalDate.parse(offerForm.getDateTo(), formatter).atStartOfDay();
        String transportationId = null;
        if (offerForm.getTransportationId() != null) {
            transportationId = offerForm.getTransportationId();
        }
        var offer = new Offer(offerForm.getNameOffer(), dateFrom, dateTo,
                offerForm.getNumSeats(), 0,
                offerForm.getAmount(),
                offerForm.getTypeRoom(), "MKD",
                offerForm.getPriceRoom(),
                offerForm.getPlaceId(), transportationId);

        return offer;
    }


    @Override
    public List<Offer> findAll() {
        return offerRepository.findAll();
    }

    /*
      Transportation and offer have @OneToOne relationship so after adding transportation to one offer (method below -> addTransportationToOffer),
      that offer no longer can be added to another transportation
    */
    @Override
    public List<Offer> findAllWithoutTransportation() {
        List<Offer> tmp = offerRepository.findAll();
        tmp.removeIf(offer -> offer.getTransportationId() != null);
        return tmp;
    }

    @Override
    public Optional<Offer> findById(OfferId id) {
        return offerRepository.findById(id);
    }

    /*
        The day when the offer is reserved must be at least 7 days before the dateFrom of the offer
        The type of the rooms that are in the offer must be the same as the type of rooms in the reservation (ONE_BED, TWO_BEDS)
        The number of rooms in the reservation must be smaller or equal then the number of rooms in the offer
     */
    @Override
    public void makeReservationForOffer
    (ReservationForm reservationForm) throws OfferIdNotExistsException {
        LocalDateTime nowDate = LocalDateTime.now();

        Offer offer = offerRepository.findById(
                OfferId.of(reservationForm.getOfferId())).orElseThrow(OfferIdNotExistsException::new);
        long days = ChronoUnit.DAYS.between(nowDate, offer.getDateFrom());
        if (days < 7) {
            throw new NotValidReservationDateException();
        }
        if (offer.getRooms().getTypeRoom().toString().equals(reservationForm.getTypeRoom())
                && offer.getRooms().getAmountRoom() >= reservationForm.getAmount()) {
            Reservation r = offer.makeReservation(reservationForm.getTypeRoom(),
                    reservationForm.getAmount(), reservationForm.getPricePerRoom()
                    , "MKD");

            offer.setRooms(offer.getRooms().reduceAmountRooms((int) reservationForm.getAmount()));
            offer.setNumSeats(offer.getNumSeats() - (int) reservationForm.getAmount());
            offerRepository.save(offer);

            //  When an offer is reserved, the variable numRoomsAgency needs to be decreased in the bounded context accommodation using domain events
            if (offer.getPlaceId()!= null)
                domainEventPublisher.publish(new ReservedRoomsForPlace(offer.getPlaceId().getId(), (int) reservationForm.getAmount()));

            //  When an offer is reserved, the variable numSeatsAgency needs to be decreased in the bounded context transportation using domain events
            if (offer.getTransportationId() != null)
                domainEventPublisher.publish(new ReservedSeatsForTransportation(offer.getTransportationId().getId(), (int) reservationForm.getAmount()));
     /*
            THIS CODE WON'T WORK BECAUSE I DON'T HAVE FORMS IN THE FRONTEND FOR AGENCY
            String agencyId = "";
            if (offer.getAgencyId() != null) {
                agencyId = offer.getAgencyId().getId();
            }
            domainEventPublisher.publish(new ReservedOffersForAgency(
                    agencyId, r.getTotalPrice().getAmount()));
    */
        } else {
            throw new UnsuccessfulReservationException();
        }

    }

    @Override
    public void deleteReservationForOffer(OfferId offerId, ReservationId reservationId) throws OfferIdNotExistsException, ReservationIdNotExistsException {
        Offer offer = offerRepository.findById(offerId).orElseThrow(OfferIdNotExistsException::new);
        offer.removeReservation(reservationId);
        offerRepository.saveAndFlush(offer);
    }

    @Override
    public List<Offer> chooseOfferToReservate(String typeRoom, double amountRooms) {
        List<Offer> result = offerRepository.findAll().stream().filter(offer ->
                offer.getRooms().getAmountRoom() == amountRooms &&
                        offer.getRooms().getTypeRoom() == TypeRoom.valueOf(typeRoom)).collect(Collectors.toList());
        return result;
    }

    @Override
    public void deleteById(OfferId id) {
        offerRepository.deleteById(id);
    }

    @Override
    public Optional<Offer> edit(OfferForm offerForm, OfferId offerId) {
        Offer offer = this.offerRepository.findById(offerId).orElseThrow(() -> new OfferIdNotExistsException());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateFrom = LocalDate.parse(offerForm.getDateFrom(), formatter).atStartOfDay();
        LocalDateTime dateTo = LocalDate.parse(offerForm.getDateTo(), formatter).atStartOfDay();

        offer.setDateFrom(dateFrom);
        offer.setDateTo(dateTo);
        offer.setNumSeats(offerForm.getNumSeats());
        var pricePerRoom = new Money(Currency.valueOf("MKD"), offerForm.getPriceRoom());
        var room = new Room(TypeRoom.valueOf(offerForm.getTypeRoom()), offerForm.getAmount(), pricePerRoom);
        offer.setRooms(room);
        this.offerRepository.save(offer);
        return Optional.of(offer);
    }

    @Override
    public void addTransportationToOffer(String transportationId, String offerId) {
        OfferId offerid = OfferId.of(offerId);
        Offer offer = this.offerRepository.findById(offerid)
                .orElseThrow(OfferIdNotExistsException::new);
        offer.setTransportationId(TransportationId.of(transportationId));
    }

}
