package com.uniovi.sdi.controllers;

import com.uniovi.sdi.services.RolesService;
import com.uniovi.sdi.services.SecurityService;
import com.uniovi.sdi.services.UsersService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.uniovi.sdi.entities.*;
import com.uniovi.sdi.validators.SignUpFormValidator;



@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private RolesService rolesService;


    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }
    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
      //model.addAttribute("usersList", usersService.getUsers());
        return "user/add";
    }
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user);
        return "redirect:/user/list";
    }
    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }
    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }
    @RequestMapping(value = "/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        return "user/edit";
    }
    @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(Model model, @PathVariable Long id, @ModelAttribute User user) {
        User base = usersService.getUser(id);
        base.setDni(user.getDni());
        base.setLastName(user.getLastName());
        base.setName(user.getName());
        usersService.updateUser(base); // ejercicio complementario 1 sesion 3
        return "redirect:/user/details/" + id;
    }


    @RequestMapping(value = "/signup", method = {RequestMethod.POST})
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user,result);
        if(result.hasErrors()){
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        model.addAttribute("markList", activeUser.getMarks());
        return "home";
    }
}
