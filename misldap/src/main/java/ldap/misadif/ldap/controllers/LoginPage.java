package ldap.misadif.ldap.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPage {

//    @RequestMapping(value = "/admins/login", method = RequestMethod.GET)

    @GetMapping(path = "/login")
    public String loginPage(){
        return "admins/login";
    }

    @GetMapping(path = "/layout/home")
    public String home(){
        return "admins/layout/home";
    }
}
