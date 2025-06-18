package com.testtask.demo.controller;

import com.testtask.demo.dto.HotelShortDto;
import com.testtask.demo.entity.Hotel;
import com.testtask.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ui")
public class HotelViewController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotels")
    public String showHotels(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels();
        List<HotelShortDto> hotelShortDtos = hotels.stream().map(hotel -> {
            String fullAddress = String.format("%d %s, %s, %s",
                    hotel.getHouseNumber(), hotel.getStreet(), hotel.getCity(), hotel.getPostCode());
            return new HotelShortDto(hotel.getId(), hotel.getName(), hotel.getDescription(), fullAddress, hotel.getPhone());
        }).collect(Collectors.toList());

        model.addAttribute("hotels", hotelShortDtos);
        return "hotels";
    }

    @GetMapping("/hotel/{id}")
    public String showHotelDetails(@PathVariable Long id, Model model) {
        Optional<Hotel> hotelOpt = hotelService.getHotelById(id);
        if (hotelOpt.isPresent()) {
            model.addAttribute("hotel", hotelOpt.get());
            return "hotel-details";
        }
        return "error";
    }

    @GetMapping("/create-hotel")
    public String showCreateHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "create-hotel";
    }

    @PostMapping("/create-hotel")
    public String createHotel(@ModelAttribute Hotel hotel) {
        hotelService.createHotel(hotel);
        return "redirect:/ui/hotels";
    }
}