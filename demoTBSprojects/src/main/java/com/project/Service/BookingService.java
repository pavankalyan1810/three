package com.project.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Model.Booking;
import com.project.Model.BookingCuisine;
import com.project.Model.Cuisine;
import com.project.Model.Table;
import com.project.Repository.BookingRepository;
import com.project.Repository.CuisineRepository;
import com.project.Repository.TableRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private CuisineRepository cuisineRepository; // Autowire the CuisineRepository
    
    public String bookTable(Long tableId, List<Long> cuisineIds, LocalDate date, LocalTime time) {
        Optional<Table> optionalTable = tableRepository.findById(tableId);
        
        if (optionalTable.isPresent()) {
            Table table = optionalTable.get();
            
            if (table.isBooked()) {
                return "Table is already booked";
            } 
            
            Booking booking = new Booking();
            booking.setTable(table);
            booking.setDate(date);
            booking.setTime(time);
            
            for (Long cuisineId : cuisineIds) {
                Optional<Cuisine> optionalCuisine = cuisineRepository.findById(cuisineId);
                if (optionalCuisine.isPresent()) {
                    Cuisine cuisine = optionalCuisine.get();
                    BookingCuisine bookingCuisine = new BookingCuisine();
                    bookingCuisine.setCuisine(cuisine);
                    bookingCuisine.setBooking(booking);
                    booking.getBookedCuisines().add(bookingCuisine);
                } else {
                    return "Cuisine with ID " + cuisineId + " not found";
                }
            }
            
            bookingRepository.save(booking);
            table.setBooked(true);
            tableRepository.save(table);
            
            return "success";
        } else {
            return "Table not found";
        }
    }
}