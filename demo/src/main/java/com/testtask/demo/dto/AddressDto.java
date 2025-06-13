package com.testtask.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddressDto {

    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;

}