package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.services.GameStudioServices;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

    @Autowired
    private GameStudioServices gss;

    Player loggedUser = null;
    private String user = "trip";
    private final String PASS = "heslo";

    @RequestMapping("/login")
    public String login(String login, String password) {


        login = login.trim();
        password = password.trim();

        Player p;
        try {
            p = gss.playerService.getPlayerByUsername(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //TODO better user management
        if (p != null && password.equals(PASS)) {
            gss.currentPlayer=p;
            loggedUser = p;
            return "redirect:/gamestudio";

        }

        return logout();
    }

    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/gamestudio";
    }

    public String  getLoggedUser() {
        return loggedUser.getName();
    }
    public Player  getLoggedUserObject() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }

}
