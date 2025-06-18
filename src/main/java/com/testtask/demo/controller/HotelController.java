package com.testtask.demo.controller;

import com.testtask.demo.dto.*;
import com.testtask.demo.entity.Hotel;
import com.testtask.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/property-view")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelShortDto>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        List<HotelShortDto> result = hotels.stream()
                .map(this::convertToShortDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    private HotelShortDto convertToShortDto(Hotel hotel) {
        String fullAddress = String.format("%d %s, %s, %s",
                hotel.getHouseNumber(), hotel.getStreet(), hotel.getCity(), hotel.getPostCode());
        return new HotelShortDto(hotel.getId(), hotel.getName(), hotel.getDescription(), fullAddress, hotel.getPhone());
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDetailDto> getHotelById(@PathVariable Long id) {
        Optional<Hotel> hotelOpt = hotelService.getHotelById(id);
        if (hotelOpt.isPresent()) {
            Hotel hotel = hotelOpt.get();
            HotelDetailDto detailDto = new HotelDetailDto();
            detailDto.setId(hotel.getId());
            detailDto.setName(hotel.getName());
            detailDto.setDescription(hotel.getDescription());
            detailDto.setBrand(hotel.getBrand());

            AddressDto addressDto = new AddressDto();
            addressDto.setHouseNumber(hotel.getHouseNumber());
            addressDto.setStreet(hotel.getStreet());
            addressDto.setCity(hotel.getCity());
            addressDto.setCountry(hotel.getCountry());
            addressDto.setPostCode(hotel.getPostCode());
            detailDto.setAddress(addressDto);

            ContactDto contactDto = new ContactDto();
            contactDto.setPhone(hotel.getPhone());
            contactDto.setEmail(hotel.getEmail());
            detailDto.setContacts(contactDto);

            ArrivalTimeDto arrivalTimeDto = new ArrivalTimeDto();
            arrivalTimeDto.setCheckIn(hotel.getCheckIn());
            arrivalTimeDto.setCheckOut(hotel.getCheckOut());
            detailDto.setArrivalTime(arrivalTimeDto);

            detailDto.setAmenities(hotel.getAmenities());

            return ResponseEntity.ok(detailDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelShortDto>> searchHotels(@RequestParam Map<String, String> params) {
        List<Hotel> hotels = hotelService.searchHotels(params);
        List<HotelShortDto> result = hotels.stream()
                .map(this::convertToShortDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelShortDto> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = hotelService.createHotel(hotel);
        return ResponseEntity.ok(convertToShortDto(createdHotel));
    }

    @PostMapping("/hotels/{id}/amenities")
    public ResponseEntity<HotelShortDto> addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        Hotel hotel = hotelService.updateAmenities(id, amenities);
        if (hotel != null) {
            return ResponseEntity.ok(convertToShortDto(hotel));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/histogram/{param}")
    public ResponseEntity<Map<String, Long>> getHistogram(@PathVariable String param) {
        Map<String, Long> histogram = hotelService.getHistogram(param);
        return ResponseEntity.ok(histogram);
    }
}