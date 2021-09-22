package mk.ukim.finki.emt.offermanagement.port.client;

import mk.ukim.finki.emt.offermanagement.domain.valueobjects.Transportation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

/*
    The offer bounded context makes request to the transport bounded context
    for calculating the offerPrice and for creating offer in DataInitializer
 */
@Service
public class TransportationClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public TransportationClient(@Value("${app.transportation.url}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public Transportation getTransportation(String id) {
        try {
            return restTemplate.exchange(uri().path("/api/transportations/get/" + id).build().toUri(),
                    HttpMethod.GET, null, new ParameterizedTypeReference<Transportation>() {
                    }).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /*
        This request is made by the offer bounded context to create initial offer in DataInitializer
        and to calculate PriceOffer (accommodationPrice + transportationFare)
        With having only transportId in the offer bounded context, we can't get the transportFare so we need to make this request
        Calculation of price offer can be seen in OfferServiceImplTest (this class is located in the test folder in offer-management)
    */
    public List<Transportation> findAll() {
        try {
            return restTemplate.exchange(uri().path("/api/transportations/forms").build().toUri(),
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Transportation>>() {
                    }).getBody();
        } catch (Exception e) {
            return null;
        }
    }

}
