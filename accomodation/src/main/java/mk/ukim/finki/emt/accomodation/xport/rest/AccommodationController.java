package mk.ukim.finki.emt.accomodation.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.accomodation.service.forms.AccommodationForm;
import mk.ukim.finki.emt.accomodation.domain.models.Accommodation;
import mk.ukim.finki.emt.accomodation.domain.models.PlaceId;
import mk.ukim.finki.emt.accomodation.service.AccommodationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/{id}")
    public List<Accommodation> getAllForPlace(@PathVariable String id) {
        return accommodationService.findByPlace(PlaceId.of(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Accommodation> save(@RequestBody AccommodationForm accommodationForm) {
        return this.accommodationService.save(accommodationForm)
                .map(offer -> ResponseEntity.ok().body(offer))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable String id) {
        this.accommodationService.deleteById(id);
    }

    // This request is made by the offer bounded context to calculate PriceOffer (accommodationPrice + transportationFare)
    @GetMapping("/form/{id}")
    public List<AccommodationForm> getAllFormsForPlace(@PathVariable String id) {
        return accommodationService.findAllByPlace(PlaceId.of(id));
    }

}
