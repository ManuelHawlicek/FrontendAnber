package io.everyonecodes.anber.controller;

import io.everyonecodes.anber.data.User;
import io.everyonecodes.anber.repository.UserRepository;
import io.everyonecodes.anber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal Principal loggedUser,
                              Model model) {
    User user = userService.loadLoggedInUser(loggedUser);
    model.addAttribute("user", user);

    return "account-form";
    }

//    @PostMapping("/account/update")
//    public String saveDetails(User user, RedirectAttributes redirectAttributes,
//                              @AuthenticationPrincipal Principal loggedUser,
//                              @RequestParam("image")MultipartFile multipartFile) throws IOException {
//
//
//    }

//    @GetMapping("/delete")
//    public String deleteHome(Principal principal, Model model) {
//        User user = userService.loadLoggedInUser(principal);
//        userRepository.delete(user);
//        return "redirect:/index";
//    }

    @GetMapping("/delete")
    public String deleteUser(Principal principal, Model model) {
        User user = userService.loadLoggedInUser(principal);
        userRepository.delete(user);
        return "redirect:/index";
    }
}
