package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.services.GameStudioServices;
import sk.tuke.gamestudio.services.RatingService;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceREST {
    @Autowired
    RatingService ratingService;

    @Autowired
    GameStudioServices gss;

    @RequestMapping(method = RequestMethod.POST)
    void addRating(@RequestBody String[] gameRating) {
        try {
            gss.setGameName(gameRating[0]);
            gss.ratingService.addRating(gss, Integer.parseInt(gameRating[1]));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // http://localhost:8080/api/Rating/mines
    @RequestMapping(value = "/{game}/{user}", method = RequestMethod.GET)
    Rating getUserRating(@PathVariable String game,@PathVariable String user) {
        try {
            return gss.ratingService.getRating(gss.playerService.getPlayerByUsername(user), game);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // http://localhost:8080/api/Rating/mines
    @RequestMapping(value = "/{game}", method = RequestMethod.GET)
    float getAvgRatings(@PathVariable String game) {
        try {
            return gss.ratingService.getAvgRating(game);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // http://localhost:8080/api/Rating?game=Mines
//    @RequestMapping
//    List<Rating> getBestRatings(@RequestParam(name="game") String game) {
//        return ratingService.getBestRatings(game);
//    }
}
