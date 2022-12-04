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
    private final String PASS = "heslo";

    @RequestMapping("/register")
    //TODO
    public String registerUser( String login, String password, String repeat, String about, Integer countryId, Integer occupationId) {
//        gss.init.status();

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

        //username size
        if(login.length()<3){
            System.out.println("Name is too short.");
            return "redirect:/gamestudio?action=register";
        }
        if(login.length()>32){ //player.name is limited to 32 by JPA -_-
            System.out.println("Name is too long.");
            return "redirect:/gamestudio?action=register";
        }

        //password size
        if(password.length()<3){
            System.out.println("Pass is too short.");
            return "redirect:/gamestudio?action=register";
        }
        if(password.length()>32){
            System.out.println("Pass is too long.");
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
        gss.currentPlayer=player;
        gss.setPassword(password);
        loggedUser=player;

        register = true;
        return "redirect:/gamestudio?action=register";
    }

    @RequestMapping("/login")
    public String login(String login, String password) {
//        gss.init.status();


        System.out.println("ph: "+gss.generateHash(login));


        login = login.trim();
        password = password.trim();

        if(login.length()<3||login.length()>32){
            System.out.println("wrong login size");
            return "redirect:/gamestudio?action=login";
        }

//        gss.init.status();

        Player p;
        try {
            p = gss.playerService.getPlayerByUsername(login);
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        if(p==null){
            return "redirect:/gamestudio?action=register";
        }
        else{
            gss.currentPlayer=p;
        }

        //TODO better user management
        if (p != null) {
            if(gss.isCorrectPass(password)) {
                loggedUser = p;
                return "redirect:/welcome";
            }

        }


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


}
