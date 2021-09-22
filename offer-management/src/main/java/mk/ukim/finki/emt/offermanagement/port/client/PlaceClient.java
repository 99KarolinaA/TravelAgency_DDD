package mk.ukim.finki.emt.offermanagement.port.client;

import mk.ukim.finki.emt.offermanagement.domain.valueobjects.Accommodation;
import mk.ukim.finki.emt.offermanagement.domain.valueobjects.Place;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

/*
    The offer bounded context makes request to the accommodation bounded context
    for calculating the offerPrice and for creating offer in DataInitializer
 */
@Service
public class PlaceClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public PlaceClient(@Value("${app.place.url}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    /*
     These requests are made by the offer bounded context to create initial offer in DataInitializer
     and to calculate PriceOffer (accommodationPrice + transportationFare)
     With having only placeId in the offer bounded context, we can't get the accommodationPrice so we need to make this request
     Calculation of price offer can be seen in OfferServiceImplTest (this class is located in the test folder in offer-management)
 */
    public List<Place> findAll() {
        try {
            return restTemplate.exchange(uri().path("/api/places").build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<Place>>() {
            }).getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    public List<Accommodation> findAccommodationsByPlace(String placeId) {
        try {
            return restTemplate.exchange(uri().path("/api/accommodations/form/"+placeId).build().toUri(),
                    HttpMethod.GET,null, new ParameterizedTypeReference<List<Accommodation>>() {
            }).getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
