
package com.vehicle.vehicle_service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VehicleController {

    private final VehicleRepository vehicleRepository;

    public VehicleController(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @GetMapping("/vehicles")
    public String showVehicles(Model model) {
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("vehicle", new Vehicle());
        return "vehicles";
    }

    @PostMapping("/vehicles/save")
    public String saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/vehicles/edit/{id}")
    public String editVehicle(@PathVariable Long id, Model model) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle ID"));

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("vehicles", vehicleRepository.findAll());

        return "vehicles";
    }

    @PostMapping("/vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleRepository.deleteById(id);
        return "redirect:/vehicles";
    }
}
