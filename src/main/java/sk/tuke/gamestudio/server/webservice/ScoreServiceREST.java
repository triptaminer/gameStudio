package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.services.ScoreService;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceREST {


    @Autowired
    ScoreService scoreService;

    @RequestMapping(method = RequestMethod.POST)
    void addScore(@RequestBody Score score) throws FileNotFoundException, SQLException {
        scoreService.addScore(score);
    }

    @RequestMapping("/{gameName}")
    List<Score> getBestScores(@PathVariable String gameName) throws FileNotFoundException, SQLException {
        return scoreService.getBestScores(gameName);
    }

}
