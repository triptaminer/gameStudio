package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.services.GameStudioServices;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GameStudioController {

    private boolean register;

    @Autowired
    GameStudioServices gss;

    @RequestMapping("/gamestudio")
    public String mainPage(String action) {
        register=(action.equals("register"));

        return "gamestudio";
    }

    @RequestMapping("/welcome")
    public String mainPageEmpty() {
        register=false;
        return "redirect:/gamestudio?action=welcome";
//        return "gamestudio";

    }

    public boolean isRegister() {
        return register;
    }
}
