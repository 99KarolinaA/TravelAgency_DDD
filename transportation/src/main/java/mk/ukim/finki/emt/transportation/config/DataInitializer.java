package mk.ukim.finki.emt.transportation.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mk.ukim.finki.emt.transportation.service.forms.TransportationForm;
import mk.ukim.finki.emt.transportation.service.TransportationService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Getter
@AllArgsConstructor
public class DataInitializer {
    private final TransportationService service;

    @PostConstruct
    public void initData() {
        TransportationForm transportationForm = new TransportationForm("name",
                20, (long) 1000, 5, "BUS", null);
        service.save(transportationForm);


    }
}
