package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Country;
import sk.tuke.gamestudio.entity.Occupation;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.services.GameStudioServices;
import sk.tuke.gamestudio.services.Init;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

    @Autowired
    private GameStudioServices gss;
    private boolean register;

    Player loggedUser = null;
    //private String user = "trip";
    private final String PASS = "heslo";

    @RequestMapping("/login")
    public String login(String login, String password) {


        login = login.trim();
        password = password.trim();

        Player p;
        try {
            p = gss.playerService.getPlayerByUsername(login);
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //TODO better user management
        if (p != null && password.equals(PASS)) {
            gss.currentPlayer=p;
            loggedUser = p;
            gss.init.status();
            return "redirect:/welcome";

        }
        gss.init.status();


        return logout();
    }

    @RequestMapping("/logout")
    public String logout() {
        loggedUser = null;
        return "redirect:/gamestudio?action=login";
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


    @RequestMapping("/register")
    //TODO
    public String registerUser( String login, String password, String repeat, String about, Integer countryId, Integer occupationId) {

        login=login.trim();
        password=password.trim();
        repeat=repeat.trim();
        about=about.trim();

        if(login.equals("") || password.equals("") || repeat.equals("") || about.equals("") || countryId == null || occupationId == null ){
            //TODO: more user-friendly error handling -something empty
            System.out.println("empty");
            return "redirect:/gamestudio?action=register";
        }

        if( ! password.equals(password)){
            //TODO: more user-friendly error handling -pass!=repeat
            System.out.println("passwords");
            return "redirect:/gamestudio?action=register";
        }

        Player p= null;
        try {
            p = gss.playerService.getPlayerByUsername(login);
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        if( p != null ){
            //TODO: more user-friendly error handling -user exist
            System.out.println("user exist");
            return "redirect:/gamestudio?action=register";
        }

        Country c = null;
        c = gss.countryService.getCountryById(countryId);
        if( c == null ){
            //TODO: more user-friendly error handling -country not exist
            System.out.println("counry");
            return "redirect:/gamestudio?action=register";
        }

        Occupation o = null;
        o = gss.occupationService.getOccupationById(occupationId);
        if( o == null ){
            //TODO: more user-friendly error handling -occupation not exist
            System.out.println("occup");
            return "redirect:/gamestudio?action=register";
        }

        //ALL OK, lets register

        System.out.println("OK!");
        try {
            gss.playerService.addPlayer(
                    new Player(login,about,c,o)
            );
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        //TODO: save passwords!

        System.out.println("login");

        //log-in our new user
        Player player;
        try {
            player=gss.playerService.getPlayerByUsername(login);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loggedUser=player;
        gss.currentPlayer=player;

        register = true;
        return "redirect:/gamestudio?action=register";
    }

}
