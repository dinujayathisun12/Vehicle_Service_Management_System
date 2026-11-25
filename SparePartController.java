
package com.vehicle.vehicle_service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SparePartController {

    private final SparePartRepository sparePartRepository;

    public SparePartController(SparePartRepository sparePartRepository) {
        this.sparePartRepository = sparePartRepository;
    }

    @GetMapping("/spare-parts")
    public String showSpareParts(Model model) {
        model.addAttribute("parts", sparePartRepository.findAll());
        model.addAttribute("part", new SparePart());
        return "spare-parts";
    }

    @PostMapping("/spare-parts/save")
    public String saveSparePart(SparePart part) {
        sparePartRepository.save(part);
        return "redirect:/spare-parts";
    }

    @GetMapping("/spare-parts/edit/{id}")
    public String editSparePart(@PathVariable Long id, Model model) {

        SparePart part = sparePartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid spare part ID"));

        model.addAttribute("part", part);
        model.addAttribute("parts", sparePartRepository.findAll());

        return "spare-parts";
    }

    @PostMapping("/spare-parts/delete/{id}")
    public String deleteSparePart(@PathVariable Long id) {
        sparePartRepository.deleteById(id);
        return "redirect:/spare-parts";
    }
}
