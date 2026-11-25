package com.vehicle.vehicle_service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ServiceBookingController {

    private final ServiceBookingRepository bookingRepository;

    public ServiceBookingController(ServiceBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/bookings")
    public String showBookings(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        model.addAttribute("booking", new ServiceBooking());
        return "bookings";
    }

    @PostMapping("/bookings/save")
    public String saveBooking(ServiceBooking booking) {
        booking.setStatus("Pending");
        bookingRepository.save(booking);
        return "redirect:/bookings";
    }

    @PostMapping("/bookings/update")
    public String updateStatus(@RequestParam Long id,
                               @RequestParam String status) {

        ServiceBooking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(status);
        bookingRepository.save(booking);

        return "redirect:/bookings";
    }
}