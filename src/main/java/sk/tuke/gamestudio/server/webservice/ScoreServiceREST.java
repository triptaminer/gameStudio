package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.services.GameStudioServices;
import sk.tuke.gamestudio.services.ScoreService;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceREST {
    @Autowired
    ScoreService scoreService;

    @Autowired
    GameStudioServices gss;


    @RequestMapping(method = RequestMethod.POST)
    void addScore(@RequestBody Score score) {
        try {
            gss.scoreService.addScore(score);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // http://localhost:8080/api/score/mines
    @RequestMapping(value = "/{game}", method = RequestMethod.GET)
    List<Score> getBestScores(@PathVariable String game) {
        try {
            return gss.scoreService.getBestScores(game);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // http://localhost:8080/api/score?game=Mines
//    @RequestMapping
//    List<Score> getBestScores(@RequestParam(name="game") String game) {
//        return scoreService.getBestScores(game);
//    }
}
