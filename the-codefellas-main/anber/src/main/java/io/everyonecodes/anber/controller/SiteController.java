package io.everyonecodes.anber.controller;

import io.everyonecodes.anber.data.Home;
import io.everyonecodes.anber.data.HomeType;
import io.everyonecodes.anber.data.ResourceInfo;
import io.everyonecodes.anber.data.User;
import io.everyonecodes.anber.service.UserService;
import io.everyonecodes.anber.service.WebAppTreeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class SiteController {

    private final UserService userService;
    private List<Home> homesList;
    private List<ResourceInfo> resourceInfoList;
    private final WebAppTreeService webAppTreeService;

    public SiteController(UserService userService, WebAppTreeService webAppTreeService) {
        this.userService = userService;
        this.webAppTreeService = webAppTreeService;
    }

    @GetMapping
    String index() {
        return "index";
    }

    @GetMapping("/calculator")
    String calculator() {
        return "calculator";
    }

    @GetMapping("/about")
    String about() {
        return "about";
    }


    @GetMapping("/home")
    public String home(Model model,
                          Principal principal) {
        User user = userService.loadLoggedInUser(principal);
        homesList = user.getSavedHomes();
        model.addAttribute("homes", homesList);
        return "home";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddHomeForm(Model model) {
        model.addAttribute("title", "Add Home");
        model.addAttribute(new Home());
        model.addAttribute("homeType", HomeType.values());
        return "add";
    }

    @GetMapping("/profile")
    public String profile(Model model,
                            Principal principal) {
        User user = userService.loadLoggedInUser(principal);
        homesList = user.getSavedHomes();
        model.addAttribute("homes", homesList);
        return "profile";
    }

//    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.DELETE)
//    public String deleteUser(@PathVariable Long id) {
//        userService.deleteUserById(id);
//        return "index";
//    }

    @GetMapping("/webapptree")
    public String webapptree(Model model, Principal principal) {
        List<ResourceInfo> resourceInfoList = webAppTreeService.getResourceinfosList();
        model.addAttribute("infos", resourceInfoList);
        return "webapptree";

    }

    @DeleteMapping("/delete/{username}")
    public String empDelete(Authentication authentication, Model model) {
        Optional<User> user = userService.findUserByEmail(authentication.getPrincipal().toString());

        model.addAttribute("user", user);
        return "redirect:/index";
    }

}
