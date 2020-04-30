package com.uncc.ticket.controller;

import com.uncc.ticket.model.PersonEntity;
import com.uncc.ticket.model.UsersEntity;
import com.uncc.ticket.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "users/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerUser(Model model) {
        UsersEntity user= new UsersEntity();
        user.setPerson(new PersonEntity());
        model.addAttribute("user", user);
        return "users/registerUser";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String storeUser(@ModelAttribute(name = "user") @Valid UsersEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/registerUser";
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        try {
            usersService.registerUser(user);
        } catch (DataIntegrityViolationException e) {
            return "users/registerUser";
        }

        return "redirect:";
    }
}
