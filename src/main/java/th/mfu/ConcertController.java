package th.mfu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConcertController {
    // HashMap to store concerts with id as key
    private static HashMap<Integer, Concert> concerts = new HashMap<>();
    private static int nextId = 1;

    // Initialize date format for binding
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/concerts")
    public String listConcerts(Model model) {
        // Add all concerts to model as a list
        model.addAttribute("concerts", new ArrayList<>(concerts.values()));
        // Return template to list concerts
        return "list-concerts";
    }

    @GetMapping("/add-concert")
    public String addAConcertForm(Model model) {
        // Pass a new empty concert to the form
        model.addAttribute("concert", new Concert());
        // Return template for concert form
        return "add-concert-form";
    }

    @PostMapping("/concerts")
    public String saveConcert(@ModelAttribute Concert concert) {
        // Set ID and add concert to HashMap
        concert.setId(nextId);
        concerts.put(nextId, concert);
        // Increment nextId
        nextId++;
        // Redirect to list concerts
        return "redirect:/concerts";
    }

    @GetMapping("/delete-concert/{id}")
    public String deleteConcert(@PathVariable int id) {
        // Remove concert with given id
        concerts.remove(id);
        // Redirect to list concerts
        return "redirect:/concerts";
    }

    @GetMapping("/delete-concert")
    public String removeAllConcerts() {
        // Clear all concerts and reset id
        concerts.clear();
        nextId = 1;
        // Redirect to list concerts
        return "redirect:/concerts";
    }
}