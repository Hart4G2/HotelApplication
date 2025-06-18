package com.testtask.demo;

import com.testtask.demo.entity.Hotel;
import com.testtask.demo.repository.HotelRepository;
import com.testtask.demo.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllHotels() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("DoubleTree by Hilton Minsk");
        when(hotelRepository.findAll()).thenReturn(Arrays.asList(hotel));

        List<Hotel> hotels = hotelService.getAllHotels();
        assertFalse(hotels.isEmpty());
        assertEquals(1, hotels.size());
        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    public void testGetHotelById_Found() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Optional<Hotel> result = hotelService.getHotelById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetHotelById_NotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Hotel> result = hotelService.getHotelById(1L);
        assertFalse(result.isPresent());
        verify(hotelRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("DoubleTree");
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel created = hotelService.createHotel(hotel);
        assertEquals("DoubleTree", created.getName());
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    public void testUpdateAmenities() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setAmenities(Arrays.asList("Free parking"));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel updated = hotelService.updateAmenities(1L,
                Arrays.asList("Free parking", "Free WiFi"));
        assertNotNull(updated);
        assertTrue(updated.getAmenities().contains("Free WiFi"));
        verify(hotelRepository, times(1)).save(hotel);
    }
}
