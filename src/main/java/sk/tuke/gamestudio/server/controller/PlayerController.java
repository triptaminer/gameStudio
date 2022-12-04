package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Country;
import sk.tuke.gamestudio.entity.Hash;
import sk.tuke.gamestudio.entity.Occupation;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.services.GameStudioServices;
import sk.tuke.gamestudio.services.PlayerService;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/profile")
public class PlayerController {

    private Player activePlayerView;

    @Autowired
    GameStudioServices gss;
    @Autowired
    PlayerService playerService;
    private boolean edit=false;
    private boolean adminMenu=false;
    private boolean changeMenu=false;


    //    @RequestMapping("/{player}}")
    @RequestMapping(value = "/{player}", method = RequestMethod.GET)
    public String viewProfile(@PathVariable String player) {
        try {
            activePlayerView=playerService.getPlayerByUsername(player);
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        adminMenu=false;
        changeMenu=false;
        edit=false;

        return "profile";

    }
    @RequestMapping(value = "/{player}/edit", method = RequestMethod.GET)
    public String editProfile(@PathVariable String player) {
        edit=true;
        return "profile";
    }
    @RequestMapping(value = "/{player}/admin", method = RequestMethod.GET)
    public String adminProfile(@PathVariable String player) {
        adminMenu=true;
        return "profile";
    }
    @RequestMapping(value = "/{player}/password", method = RequestMethod.GET)
    public String changePasswordPage(@PathVariable String player) {
        changeMenu=true;
        return "profile";
    }
    @RequestMapping(value = "/{player}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable String player, String oldPassword, String newPassword, String verify) {

        System.out.println("change password event...");
        try {
            activePlayerView = playerService.getPlayerByUsername(player);
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        //a-team is able to change passwords of common users...
        //mods cant change pass for other mod/admin accs
        if( gss.currentPlayer.getPrivilege()>activePlayerView.getPrivilege()){
            System.out.println("a-team is changing password");
            GameStudioServices gssTemp=new GameStudioServices();
            gssTemp.currentPlayer=activePlayerView;
            gssTemp.setPassword(activePlayerView.getName(),newPassword);

        }
        // othervise Player activePlayerView must be same as gss.currentPlayer
        else if( gss.currentPlayer.getName().equals(activePlayerView.getName()) ){
            //FIXME: why gss.currentPlayer do not equals activePlayerView but its.getname()s do?
            System.out.println("");
            if(newPassword.equals(verify) && newPassword.length()>0 && gss.isCorrectPass(oldPassword)) {
                System.out.println("user is changing its own password");
                gss.setPassword(newPassword);
                changeMenu=false;
                edit=false;
            }
        }
        System.out.println("change password event finished.");

        return "profile";

    }

    public String profileImage() {
        //TODO: are we going to use user images?
        return "/img/noPhoto.png";
    }

    public String getUsername() {
        //TODO: are we going to use user images?
        return activePlayerView.getName();
    }

    public Player getPlayer(){
        return activePlayerView;
    }
    public String  getPlayerName(){
        System.out.println(activePlayerView.getName());
        return activePlayerView.getName();
    }

    public boolean isEditable(){
        System.out.println("is editable? "+gss.currentPlayer.getName().equals(activePlayerView.getName()));
        System.out.println(gss.currentPlayer.getName());
        System.out.println(activePlayerView.getName());
        return gss.currentPlayer.getName().equals(activePlayerView.getName());
    }

    public boolean isAdmin(){
        return gss.currentPlayer.getPrivilege()>=2;
    }

    public boolean isEdit() {
        return edit;
    }

    public boolean isAdminMenu() {
        return adminMenu;
    }

    public boolean isChangeMenu() {
        return changeMenu;
    }
}
