package com.testtask.demo.controller;

import com.testtask.demo.entity.Hotel;
import com.testtask.demo.service.HotelService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelViewController.class)
public class HotelViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Test
    public void testShowHotels() throws Exception {
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

        mockMvc.perform(get("/ui/hotels"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotels"))
                .andExpect(model().attributeExists("hotels"))
                .andExpect(model().attribute("hotels", hasSize(1)))
                .andExpect(model().attribute("hotels", hasItem(allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("name", is("DoubleTree by Hilton Minsk"))
                ))));
    }

    @Test
    public void testShowHotelDetails_Found() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("DoubleTree by Hilton Minsk");
        hotel.setDescription("Test description");
        hotel.setHouseNumber(9);
        hotel.setStreet("Pobediteley Avenue");
        hotel.setCity("Minsk");
        hotel.setPostCode("220004");
        hotel.setPhone("+375 17 309-80-00");
        hotel.setEmail("email@example.com");

        Mockito.when(hotelService.getHotelById(1L)).thenReturn(Optional.of(hotel));

        mockMvc.perform(get("/ui/hotel/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("hotel-details"))
                .andExpect(model().attributeExists("hotel"))
                .andExpect(model().attribute("hotel", hasProperty("id", is(1L))));
    }

    @Test
    public void testShowHotelDetails_NotFound() throws Exception {
        Mockito.when(hotelService.getHotelById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/ui/hotel/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    public void testCreateHotel() throws Exception {
        mockMvc.perform(post("/ui/create-hotel")
                                .param("name", "New Hotel")
                                .param("description", "New description")
                                .param("houseNumber", "10")
                                .param("street", "Some Street")
                                .param("city", "Some City")
                                .param("postCode", "12345")
                                .param("phone", "123456789")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ui/hotels"));

        Mockito.verify(hotelService, times(1)).createHotel(Mockito.any(Hotel.class));
    }
}
