package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

    String loggedUser="";
    private String user;
private final String PASS="heslo";

    @RequestMapping("/login")
    public String loginPage(String login,String password) {


        login=login.trim();
        password=password.trim();
    if(login.equals(user)&&password.equals(PASS)){
        loggedUser=login;
        return "redirect:/gamestudio";

    }

        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        loggedUser="";
        return "login";
    }

    public String getLoggedUser() {
        return loggedUser;
    }
    public boolean isLogged() {
        return loggedUser!=null;
    }

}
