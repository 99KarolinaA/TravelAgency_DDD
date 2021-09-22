package mk.ukim.finki.emt.offermanagement.port.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.offermanagement.domain.models.Offer;
import mk.ukim.finki.emt.offermanagement.domain.models.OfferId;
import mk.ukim.finki.emt.offermanagement.service.forms.OfferForm;
import mk.ukim.finki.emt.offermanagement.service.forms.ReservationForm;
import mk.ukim.finki.emt.offermanagement.service.interfaces.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping
    public List<Offer> getAll() {
        return offerService.findAll();
    }

    /*
       Transportation and offer have @OneToOne relationship so after adding transportation to one offer (method below -> addTransportation), that offer
       no longer can be added to another transportation
     */
    @GetMapping("/without")
    public List<Offer> getWithoutTransportation() {
        return offerService.findAllWithoutTransportation();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> findById(@PathVariable String id) {
        return this.offerService.findById(OfferId.of(id))
                .map(offer -> ResponseEntity.ok().body(offer))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/addTransportation")
    public void addTransportation(@RequestParam String offerId, @RequestParam String transportationId) {
        this.offerService.addTransportationToOffer(transportationId, offerId);
    }

    @PostMapping("/add")
    public ResponseEntity<Offer> save(@RequestBody OfferForm offerForm) {
        return this.offerService.createOffer(offerForm)
                .map(offer -> ResponseEntity.ok().body(offer))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Offer> save(@PathVariable String id, @RequestBody OfferForm offerForm) {
        return this.offerService.edit(offerForm, OfferId.of(id))
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable String id) {
        this.offerService.deleteById(OfferId.of(id));
        if (this.offerService.findById(OfferId.of(id)).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/reserve")
    public void reserveOffer(@RequestBody ReservationForm reservationForm) {
        this.offerService.makeReservationForOffer(reservationForm);
    }
}
