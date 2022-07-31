package io.everyonecodes.anber.controller;


import io.everyonecodes.anber.data.Home;
import io.everyonecodes.anber.data.HomeType;
import io.everyonecodes.anber.data.User;
import io.everyonecodes.anber.repository.HomeRepository;
import io.everyonecodes.anber.repository.UserRepository;
import io.everyonecodes.anber.service.HomeService;
import io.everyonecodes.anber.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final HomeService homeService;
    private final UserService userService;
    private final HomeRepository homeRepository;
    private final UserRepository userRepository;
    private static List<Home> homeList = new ArrayList<>();

    public HomeController(HomeService homeService, UserService userService, HomeRepository homeRepository, UserRepository userRepository) {
        this.homeService = homeService;
        this.userService = userService;
        this.homeRepository = homeRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "save-home-form", method = RequestMethod.GET)
    public String displayAddHomeForm(Model model) {

        model.addAttribute("title", "Add Home");
        model.addAttribute("home", new Home());
        model.addAttribute("homeType", HomeType.values());
        return "save-home-form";
    }
    @PostMapping("/save")
    public String saveHome(@ModelAttribute("home") Home home, Principal principal, Model model) {
        User user = userService.loadLoggedInUser(principal);
        homeService.addHome(home, user.getUsername());
        model.addAttribute("home", new Home());
        return "redirect:/profile";
    }

    @PostMapping("/update")
    public String updateHome(@ModelAttribute("home") Home home, Principal principal, Model model) {
        User user = userService.loadLoggedInUser(principal);
        homeRepository.save(home);
        model.addAttribute("home", new Home());
        return "redirect:/profile";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditHomePage(@PathVariable(name = "id") Long id, Principal principal, Model model) {
        ModelAndView editView = new ModelAndView("edit-home-form");
        User user = userService.loadLoggedInUser(principal);
        model.addAttribute("homeType", HomeType.values());
        Home home = homeService.findOneHome(user.getUsername(), id);
        editView.addObject("home", home);

        return editView;
    }

    @GetMapping("delete/{id}")
    public String deleteHome(@PathVariable(name = "id") Long id, Principal principal, Model model) {
        User user = userService.loadLoggedInUser(principal);
        model.addAttribute("homeType", HomeType.values());
        Home home = homeRepository.findById(id).get();
        user.getSavedHomes().remove(home);
        userRepository.save(user);
        homeRepository.delete(home);

        return "redirect:/profile";
    }
//
//    @GetMapping("/edit/{id}")
//    public String showUpdateForm(@PathVariable("id") long id, Model model) {
//        Home home = homeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid home Id:" + id));
//        model.addAttribute("home", home);
//        model.addAttribute("homeType", HomeType.values());
//
//        return "edit-home-form";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateUser(@PathVariable("id") long id, @Valid Home home, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            home.setId(id);
//            return "edit-home-form";
//        }
//
//        homeRepository.save(home);
//
//        return "redirect:/index";
//    }
//
//    @PostMapping("/save")
//    public String saveHome(@ModelAttribute("home") Home home, Principal principal) {
//        User user = userService.loadLoggedInUser(principal);
//        homeService.addHome(home, user.getUsername());
//        return "redirect:/profile";
//    }

//    @GetMapping("/edit/{id}")
//    public String showEditHomePage(@PathVariable(name = "id") Long id, Principal principal, Model model) {
//        User user = userService.loadLoggedInUser(principal);
//        model.addAttribute("homeType", HomeType.values());
//        Home home = homeService.findOneHome(user.getUsername(), id);
//        model.addAttribute("home", home)
//
//        return "edit-home";
//    }

//    @GetMapping("/delete/{id}")
//    public String deleteHome(@PathVariable(name = "id") Long id, Principal principal) {
//        User user = userService.loadLoggedInUser(principal);
//        homeService.removeHome(user.getUsername(), id);
//        return "redirect:/profile";
//    }

//    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
//    public String showEditHomePage(@PathVariable(name = "id") Long id, Principal principal, Model model) {
//        User user = userService.loadLoggedInUser(principal);
//        model.addAttribute("homeType", HomeType.values());
//        Home home = homeService.findOneHome(user.getUsername(), id);
//        model.addAttribute("home", home);
//        return "edit-home";
//    }
//
//    @RequestMapping(path = "/{id}", method = RequestMethod.POST)
//    public RedirectView updateHome(RedirectAttributes redirectAttributes, @PathVariable("id") Long id, @ModelAttribute Home home){
//        homeService.updateHome(id, home);
//        String message="fu";
//        RedirectView redirectView=new RedirectView("/",true);
//        redirectAttributes.addFlashAttribute("userMessage",message);
//        return redirectView;
//    }

//    @PostMapping("/update/{id}")
//    public String updateUser(@PathVariable("id") long id, @Valid Home home,
//                             BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            home.setId(id);
//            return "edit-home-form";
//        }
//
//        homeRepository.save(home);
//        return "redirect:/index";
//    }



}
