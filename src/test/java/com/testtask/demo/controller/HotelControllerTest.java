package com.testtask.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.demo.entity.Hotel;
import com.testtask.demo.service.HotelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllHotels() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("DoubleTree by Hilton Minsk");
        hotel.setDescription("Test description");
        hotel.setHouseNumber(9);
        hotel.setStreet("Pobediteley Avenue");
        hotel.setCity("Minsk");
        hotel.setPostCode("220004");
        hotel.setPhone("+375 17 309-80-00");

        Mockito.when(hotelService.getAllHotels()).thenReturn(Arrays.asList(hotel));

        mockMvc.perform(get("/property-view/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("DoubleTree by Hilton Minsk")));
    }

    @Test
    public void testGetHotelById_Found() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("DoubleTree by Hilton Minsk");
        hotel.setDescription("Test description");
        hotel.setBrand("Hilton");
        hotel.setHouseNumber(9);
        hotel.setStreet("Pobediteley Avenue");
        hotel.setCity("Minsk");
        hotel.setCountry("Belarus");
        hotel.setPostCode("220004");
        hotel.setPhone("+375 17 309-80-00");
        hotel.setEmail("email@example.com");
        hotel.setCheckIn("14:00");
        hotel.setCheckOut("12:00");
        hotel.setAmenities(Arrays.asList("Free WiFi", "Free parking"));

        Mockito.when(hotelService.getHotelById(1L)).thenReturn(Optional.of(hotel));

        mockMvc.perform(get("/property-view/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("DoubleTree by Hilton Minsk")))
                .andExpect(jsonPath("$.brand", is("Hilton")));
    }

    @Test
    public void testCreateHotel() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setDescription("Test description");
        hotel.setHouseNumber(1);
        hotel.setStreet("Test street");
        hotel.setCity("Test city");
        hotel.setCountry("Test country");
        hotel.setPostCode("00000");
        hotel.setPhone("123456789");
        hotel.setCheckIn("14:00");
        hotel.setCheckOut("12:00");

        Hotel savedHotel = new Hotel();
        savedHotel.setId(1L);
        savedHotel.setName("Test Hotel");
        savedHotel.setDescription("Test description");
        savedHotel.setHouseNumber(1);
        savedHotel.setStreet("Test street");
        savedHotel.setCity("Test city");
        savedHotel.setCountry("Test country");
        savedHotel.setPostCode("00000");
        savedHotel.setPhone("123456789");
        savedHotel.setCheckIn("14:00");
        savedHotel.setCheckOut("12:00");

        Mockito.when(hotelService.createHotel(Mockito.any(Hotel.class)))
                .thenReturn(savedHotel);

        mockMvc.perform(post("/property-view/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Hotel")));
    }
}