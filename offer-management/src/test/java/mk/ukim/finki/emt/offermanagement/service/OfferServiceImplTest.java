package mk.ukim.finki.emt.offermanagement.service;

import mk.ukim.finki.emt.offermanagement.domain.models.Offer;
import mk.ukim.finki.emt.offermanagement.domain.valueobjects.*;
import mk.ukim.finki.emt.offermanagement.port.client.PlaceClient;
import mk.ukim.finki.emt.offermanagement.port.client.TransportationClient;
import mk.ukim.finki.emt.offermanagement.service.forms.OfferForm;
import mk.ukim.finki.emt.offermanagement.service.interfaces.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class OfferServiceImplTest {

    @Autowired
    private OfferService service;

    @Autowired
    private PlaceClient placeClient;

    @Autowired
    private TransportationClient transportationClient;

    /*
        Example of calculating the variable priceOfferPerPerson in the entity Offer
     */
    @Test
    public void test1() {
        List<Place> placeList = placeClient.findAll();
        Place place = placeList.get(0);
        Place place2 = placeList.get(1);
        OfferForm offerForm = new OfferForm("nameOffer22", "2021-11-10", "2021-11-20", 10,
                null, "ONE_BED", 10, 100, place.getId().getId());
        Offer o = service.createOffer(offerForm).get();
        Accommodation a = placeClient.findAccommodationsByPlace(place.getId().getId()).get(0);
        double accommodationPrice = a.getPricePerPerson();

        /*
            With having only transportId/placeId, we can't get the transportFare or the accommodationPrice
            so we need to make request to the other bounded contexts
         */
        Transportation t = transportationClient.findAll().get(0);
        o.setTransportationId(TransportationId.of(t.getId()));
        double transportationFare = t.getFare();
        service.calculatePriceOffer(o.getId(), accommodationPrice + transportationFare);

    }
}
