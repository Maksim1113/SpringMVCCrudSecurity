package ru.khaustov.springcrud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.khaustov.springcrud.models.UserModel;
import ru.khaustov.springcrud.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/start")
    public String start(){
        //Authentication a = SecurityContextHolder.getContext().getAuthentication();
        return "start";
    }

    @GetMapping("/registration")
    public String regNewUser(Model model){
        model.addAttribute("user", new UserModel());
        return "registration";

    }


    @PostMapping("/registration")
    public String regUser(@ModelAttribute("user") UserModel user){
        userService.addUser(user);
        return "redirect:/start";
    }

    @GetMapping("/user")
    public String userPage(Model model){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String name = a.getName();
        UserModel userModel = userService.getUserByName(name);
        List<UserModel> users = new ArrayList<>();
        users.add(userModel);
        model.addAttribute("users", users);
        return "userInfo";

    }

    @GetMapping("/admin")
    public String getAllUsers(Model model){
        List<UserModel> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "getUsers";
    }

    @GetMapping("/admin/{id}")
    public String user(@ModelAttribute("user") UserModel edUser, Model model){
        UserModel user = userService.getUser(edUser.getId());
        model.addAttribute("user", user);
        return "newUser";
    }


    @GetMapping("/admin/new")
    public String setNewUser(Model model){
        model.addAttribute("user", new UserModel());
        return "newUser";

    }

    @PostMapping("/admin")
    public String createUser(@ModelAttribute("user") UserModel user){
        userService.addUser(user);
        return "redirect:/admin";
    }


    @DeleteMapping("/admin/{id}")
    public String deleteUser(@ModelAttribute("user") UserModel user){
        userService.deleteUser(user.getId());
        return "redirect:/admin";
    }


}
