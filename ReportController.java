package com.vehicle.vehicle_service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceBookingRepository bookingRepository;
    private final SparePartRepository sparePartRepository;
    private final BillingRepository billingRepository;
    private final ServiceHistoryRepository historyRepository;

    public ReportController(
            CustomerRepository customerRepository,
            VehicleRepository vehicleRepository,
            ServiceBookingRepository bookingRepository,
            SparePartRepository sparePartRepository,
            BillingRepository billingRepository,
            ServiceHistoryRepository historyRepository) {

        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
        this.bookingRepository = bookingRepository;
        this.sparePartRepository = sparePartRepository;
        this.billingRepository = billingRepository;
        this.historyRepository = historyRepository;
    }

    @GetMapping("/reports")
    public String showReports(Model model) {

        model.addAttribute("totalCustomers", customerRepository.count());
        model.addAttribute("totalVehicles", vehicleRepository.count());
        model.addAttribute("totalBookings", bookingRepository.count());
        model.addAttribute("totalSpareParts", sparePartRepository.count());
        model.addAttribute("totalBills", billingRepository.count());
        model.addAttribute("totalHistory", historyRepository.count());

        model.addAttribute("totalIncome",
                billingRepository.findAll().stream()
                        .filter(b -> "Paid".equalsIgnoreCase(b.getPaymentStatus()))
                        .mapToDouble(Billing::getAmount)
                        .sum());

        return "reports";
    }
}