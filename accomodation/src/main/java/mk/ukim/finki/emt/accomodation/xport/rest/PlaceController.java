package mk.ukim.finki.emt.accomodation.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.accomodation.service.forms.PlaceForm;
import mk.ukim.finki.emt.accomodation.domain.models.Place;
import mk.ukim.finki.emt.accomodation.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public List<Place> getAll() {
        return placeService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Place> save(@RequestBody PlaceForm placeForm) {
        return this.placeService.save(placeForm)
                .map(offer -> ResponseEntity.ok().body(offer))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/delete/{id}")
    public void deleteById(@PathVariable String id) {
        this.placeService.deleteById(id);
    }
}
