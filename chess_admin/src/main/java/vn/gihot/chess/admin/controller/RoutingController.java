package vn.gihot.chess.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class RoutingController {

    @RequestMapping(value = "/")
    public String index(Principal principal) {
        return principal != null ? "/views/dashboard" : "/login";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "/login";
    }

    @RequestMapping("/403")
    public String error403() {
        return "/error/403";
    }

    @RequestMapping(value = "/dashboard")
    public String dashboard(){
        return "/views/dashboard";
    }

    @RequestMapping(value = "/form-elements")
    public String formElements(){
        return "/views/form-elements";
    }

    @RequestMapping(value = "/table-elements")
    public String tableElements(){
        return "/views/table-elements";
    }

}
