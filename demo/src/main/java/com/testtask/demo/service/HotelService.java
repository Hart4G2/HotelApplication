package com.testtask.demo.service;

import com.testtask.demo.entity.Hotel;
import com.testtask.demo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateAmenities(Long id, List<String> amenities) {
        Optional<Hotel> hotelOpt = hotelRepository.findById(id);
        if (hotelOpt.isPresent()) {
            Hotel hotel = hotelOpt.get();
            hotel.setAmenities(amenities);
            return hotelRepository.save(hotel);
        }
        return null;
    }

    public List<Hotel> searchHotels(Map<String, String> params) {
        List<Hotel> hotels = hotelRepository.findAll();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey().toLowerCase();
            String value = entry.getValue().toLowerCase();
            hotels = hotels.stream().filter(h -> {
                switch (key) {
                    case "name":
                        return h.getName() != null && h.getName().toLowerCase().contains(value);
                    case "brand":
                        return h.getBrand() != null && h.getBrand().toLowerCase().equals(value);
                    case "city":
                        return h.getCity() != null && h.getCity().toLowerCase().equals(value);
                    case "country":
                        return h.getCountry() != null && h.getCountry().toLowerCase().equals(value);
                    case "amenities":
                        return h.getAmenities().stream().anyMatch(a -> a.toLowerCase().contains(value));
                    default:
                        return true;
                }
            }).collect(Collectors.toList());
        }
        return hotels;
    }

    public Map<String, Long> getHistogram(String param) {
        List<Hotel> hotels = hotelRepository.findAll();
        Map<String, Long> histogram = new HashMap<>();
        switch (param.toLowerCase()) {
            case "brand":
                histogram = hotels.stream()
                        .filter(h -> h.getBrand() != null)
                        .collect(Collectors.groupingBy(Hotel::getBrand, Collectors.counting()));
                break;
            case "city":
                histogram = hotels.stream()
                        .filter(h -> h.getCity() != null)
                        .collect(Collectors.groupingBy(Hotel::getCity, Collectors.counting()));
                break;
            case "country":
                histogram = hotels.stream()
                        .filter(h -> h.getCountry() != null)
                        .collect(Collectors.groupingBy(Hotel::getCountry, Collectors.counting()));
                break;
            case "amenities":
                histogram = hotels.stream()
                        .flatMap(h -> h.getAmenities().stream())
                        .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
                break;
            default:
                break;
        }
        return histogram;
    }
}