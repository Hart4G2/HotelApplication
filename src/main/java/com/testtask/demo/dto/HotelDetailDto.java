package com.testtask.demo.dto;

import java.util.List;

public class HotelDetailDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressDto address;
    private ContactDto contacts;
    private ArrivalTimeDto arrivalTime;
    private List<String> amenities;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public AddressDto getAddress() {
        return address;
    }
    public void setAddress(AddressDto address) {
        this.address = address;
    }
    public ContactDto getContacts() {
        return contacts;
    }
    public void setContacts(ContactDto contacts) {
        this.contacts = contacts;
    }
    public ArrivalTimeDto getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(ArrivalTimeDto arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public List<String> getAmenities() {
        return amenities;
    }
    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }
}
