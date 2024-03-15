package com.project.Controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.Model.Cuisine;
import com.project.Service.CuisineService;
 

 

 
@Controller
@RequestMapping("/cuisines")
public class CuisineController {
 
    @Autowired
    private CuisineService cuisineService;
 
    @GetMapping
    public String getAllCuisines(Model model) {
        List<Cuisine> cuisines = cuisineService.getAllCuisines();
        model.addAttribute("cuisines", cuisines);
        return "cuisineList"; // HTML view name
    }
 
    @GetMapping("/{id}")
    public String getCuisineById(@PathVariable Long id, Model model) {
        Optional<Cuisine> cuisine = cuisineService.getCuisineById(id);
        if (cuisine.isPresent()) {
            model.addAttribute("cuisine", cuisine.get());
            return "cuisineDetails"; // HTML view name for displaying single cuisine details
        } else {
            // Handle case where cuisine with given ID is not found
            return "error"; // HTML view name for error page
        }
    }
 
    @GetMapping("/add")
    public String addCuisineForm(Model model) {
        model.addAttribute("cuisine", new Cuisine());
        return "addCuisineForm"; // HTML view name for add cuisine form
    }

    @PostMapping("/add")
    public String addCuisine(Cuisine cuisine) {
        cuisineService.addCuisine(cuisine);
        return "redirect:/cuisines"; // Redirect to the list of all cuisines
    }
    
//    @PostMapping("/{id}/update")
//    public String updateCuisine(@PathVariable Long id, Cuisine cuisine) {
//        cuisine.setCuisineId(id); // Ensure the correct ID is set for update
//        cuisineService.updateCuisine(cuisine);
//        return "redirect:/cuisines"; // Redirect to the list of all cuisines
//}
    
    
    @PostMapping("/{id}/update")
    public String updateCuisine(@PathVariable Long id, Model model) {
        Optional<Cuisine> cuisine = cuisineService.getCuisineById(id);
        if (cuisine.isPresent()) {
            model.addAttribute("cuisine", cuisine.get());
            return "updateCuisine"; // Redirect to the update page
        } else {
            // Handle case where cuisine with given ID is not found
            return "error"; // HTML view name for error page
        }
    }
    
//    @PostMapping("/{id}/save")
//    public String saveCuisineChanges(@PathVariable Long id, Cuisine updatedCuisine) {
//        updatedCuisine.setCuisineId(id); // Ensure the correct ID is set for update
//        cuisineService.updateCuisine(updatedCuisine);
//        return "redirect:/cuisines"; // Redirect to the list of all cuisines
//    }

    @PostMapping("/{id}/save")
    public String saveCuisineChanges(@PathVariable Long id, Cuisine updatedCuisine, Model model) {
        updatedCuisine.setCuisineId(id); // Ensure the correct ID is set for update
        cuisineService.updateCuisine(updatedCuisine);
        
        // Get the updated list of cuisines and add it to the model
        List<Cuisine> cuisines = cuisineService.getAllCuisines();
        model.addAttribute("cuisines", cuisines);
        
        return "redirect:/cuisines"; // Redirect to the list of all cuisines
    }


 

    
    
    @PostMapping("/{id}/delete")
    public String deleteCuisine(@PathVariable Long id) {
        cuisineService.deleteCuisine(id);
        return "redirect:/cuisines"; // Redirect to the list of all cuisines
    }
}
