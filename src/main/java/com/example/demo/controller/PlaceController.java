package com.example.demo.controller;

import com.example.demo.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@CrossOrigin
public class PlaceController {
    @Autowired
    private PlaceService placeService;
    @GetMapping("/countries")
    public Object[] getCountries() {
        return placeService.getCountries();
    }
    @GetMapping("/states={states}")
    public Object[] getStates(@PathVariable("states") String states) {
        return placeService.getStates(states);
    }
    @GetMapping("/cities={cities}")
    public Object[] getCities(@PathVariable("cities") String cities) {
        return placeService.getCities(cities);
    }
}
