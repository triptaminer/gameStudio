package sk.tuke.gamestudio.services;

import org.junit.Test;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class ScoreServiceTest {

    private final ScoreService scoreService = new ScoreServiceJDBC();

    @Test
    public void testReset(){

        scoreService.addScore(new Score(0,"Mines","trew",123, new Date()));

        //assertTrue(scoreService);

    }


}
