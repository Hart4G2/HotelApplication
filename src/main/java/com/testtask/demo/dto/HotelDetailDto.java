package com.testtask.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class HotelDetailDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressDto address;
    private ContactDto contacts;
    private ArrivalTimeDto arrivalTime;
    private List<String> amenities;

}
