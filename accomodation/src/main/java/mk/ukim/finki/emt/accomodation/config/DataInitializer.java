package mk.ukim.finki.emt.accomodation.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mk.ukim.finki.emt.accomodation.service.forms.AccommodationForm;
import mk.ukim.finki.emt.accomodation.service.forms.PlaceForm;
import mk.ukim.finki.emt.accomodation.domain.models.Place;
import mk.ukim.finki.emt.accomodation.service.AccommodationService;
import mk.ukim.finki.emt.accomodation.service.PlaceService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Getter
@AllArgsConstructor
public class DataInitializer {
    private final PlaceService placeService;
    private final AccommodationService accommodationService;

    @PostConstruct
    public void initData() {
        PlaceForm placeForm1 = new PlaceForm("name1", 10);
        PlaceForm placeForm2 = new PlaceForm("name2", 20);
        Place p1 = placeService.save(placeForm1).get();
        Place p2 = placeService.save(placeForm2).get();

        AccommodationForm accommodationForm1 = new AccommodationForm("nameAccommodation1",
                "address", "APARTMENT", (long) 1000, p1.getId().getId());
        AccommodationForm accommodationForm2 = new AccommodationForm("nameAccommodation2",
                "address2", "HOTEL", (long) 2000, p1.getId().getId());
        AccommodationForm accommodationForm3 = new AccommodationForm("nameAccommodation3",
                "address3", "MOTEL", (long) 3000, p2.getId().getId());
        accommodationService.save(accommodationForm1);
        accommodationService.save(accommodationForm2);
        accommodationService.save(accommodationForm3);


    }
}
