package mk.ukim.finki.emt.offermanagement.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mk.ukim.finki.emt.offermanagement.domain.valueobjects.*;
import mk.ukim.finki.emt.offermanagement.port.client.PlaceClient;
import mk.ukim.finki.emt.offermanagement.port.client.TransportationClient;
import mk.ukim.finki.emt.offermanagement.service.forms.OfferForm;
import mk.ukim.finki.emt.offermanagement.service.interfaces.OfferService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Getter
@AllArgsConstructor
public class DataInitializer {
    private final OfferService offerService;
    private final PlaceClient placeClient;
    private final TransportationClient transportationClient;

    @PostConstruct
    public void initData() {
        List<Place> placeList = placeClient.findAll();
        Place place = placeList.get(0);
        Transportation t = transportationClient.findAll().get(0);
        OfferForm offerForm = new OfferForm("nameOffer", "2021-12-10", "2021-12-20", 10,
                t.getId(), "ONE_BED", 10, 1000, place.getId().getId());
        offerService.createOffer(offerForm);
    }
}
