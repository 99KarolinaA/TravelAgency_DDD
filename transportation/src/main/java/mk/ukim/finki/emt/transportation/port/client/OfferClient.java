package mk.ukim.finki.emt.transportation.port.client;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class OfferClient {

    /*
      The transportation bounded context makes request to the offer bounded context
      This request adds the given transportation to the given offer (sets the variable transportationId of the offer)
     */
    public void findOffer(String offerId, String transportationId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9090/api/offers/addTransportation";

        // URI (URL) parameters
        Map<String, String> urlParams = new HashMap<>();

        // Query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("offerId", offerId)
                .queryParam("transportationId", transportationId);

        System.out.println(builder.buildAndExpand(urlParams).toUri());

        restTemplate.exchange(builder.build().toUri(), HttpMethod.GET,
                null, Void.class);
    }

}