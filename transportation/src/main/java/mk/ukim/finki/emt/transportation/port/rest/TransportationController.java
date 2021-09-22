package mk.ukim.finki.emt.transportation.port.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.transportation.port.client.OfferClient;
import mk.ukim.finki.emt.transportation.service.forms.TransportationForm;
import mk.ukim.finki.emt.transportation.domain.model.Transportation;
import mk.ukim.finki.emt.transportation.service.TransportationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportations")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TransportationController {
    private final TransportationService transportationService;
    private final OfferClient offerClient;

    @GetMapping
    public List<Transportation> getAll() {
        return transportationService.findAll();
    }

    // This request is made by the offer bounded context to calculate PriceOffer (accommodationPrice + transportationFare)
    @GetMapping("/forms")
    public List<TransportationForm> getAllForms() {
        return transportationService.findAllForms();
    }

    @PostMapping("/add")
    public Transportation save(@RequestBody TransportationForm transportationForm) {
        Transportation t = this.transportationService.save(transportationForm).get();
        this.offerClient.findOffer(t.getOfferId(), t.getId().getId());
        return t;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable String id) {
        this.transportationService.deleteById(id);
    }

    @GetMapping("/get/{id}")
    public TransportationForm getTransportation(@PathVariable String id) {
        return transportationService.findById(id);
    }

}
