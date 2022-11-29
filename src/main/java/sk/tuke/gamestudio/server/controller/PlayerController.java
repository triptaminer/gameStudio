package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Country;
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


    //    @RequestMapping("/{player}}")
@RequestMapping(value = "/{player}", method = RequestMethod.GET)
    public String viewProfile(@PathVariable String player) {
        try {
            activePlayerView=playerService.getPlayerByUsername(player);
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

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

}
