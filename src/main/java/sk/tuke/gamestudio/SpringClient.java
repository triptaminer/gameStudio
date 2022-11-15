package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.entity.Occupation;
import sk.tuke.gamestudio.services.*;

@SpringBootApplication
//@Configuration
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class, args!=null?"y":"n");
    }

    //@Bean //uncoment for load Occupation
    public CommandLineRunner runnerSimple(){
        return args -> {
            System.out.println("hell ouch lets hack the world!");
            gameStudioServices().occupationService.addOccupation(new Occupation("pupil"));
            gameStudioServices().occupationService.addOccupation(new Occupation("student"));
            gameStudioServices().occupationService.addOccupation(new Occupation("employee"));
            gameStudioServices().occupationService.addOccupation(new Occupation("unemployed"));
            gameStudioServices().occupationService.addOccupation(new Occupation("self-employed"));
            gameStudioServices().occupationService.addOccupation(new Occupation("retired"));
            gameStudioServices().occupationService.addOccupation(new Occupation("invalid"));
            gameStudioServices().occupationService.addOccupation(new Occupation("other"));
        };

    }
    @Bean //uncoment for start game
    public CommandLineRunner runnerGameStudioConsole(GameStudioConsole gameStudioConsole){
        return args -> {
            gameStudioConsole().run();
        };
    }



    //FIXME grrr... @Bean
    public CommandLineRunner runnerGameStudioConsoleWithDB(GameStudioConsole gameStudioConsole,String db){
        return args -> {
            if(db=="y"){
                //with param we fill database first :)
                System.out.println("setting DB");
                gameStudioServices().occupationService.addOccupation(new Occupation("pupil"));
                gameStudioServices().occupationService.addOccupation(new Occupation("student"));
                gameStudioServices().occupationService.addOccupation(new Occupation("employee"));
                gameStudioServices().occupationService.addOccupation(new Occupation("unemployed"));
                gameStudioServices().occupationService.addOccupation(new Occupation("self-employed"));
                gameStudioServices().occupationService.addOccupation(new Occupation("retired"));
                gameStudioServices().occupationService.addOccupation(new Occupation("invalid"));
                gameStudioServices().occupationService.addOccupation(new Occupation("other"));
            }

            gameStudioConsole().run();
        };
    }

    @Bean
    public GameStudioConsole gameStudioConsole(){
        return new GameStudioConsole();
    }

    @Bean
    public GameStudioServices gameStudioServices(){
        return new GameStudioServices();
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService rankService(){
        return new RatingServiceJPA();
    }

    @Bean
    public PlayerService playerService(){
        return new PlayerServiceJPA();
    }

    @Bean
    public CountryService countryService(){
        return new CountryServiceJPA();
    }

    @Bean
    public OccupationService occupationService(){
        return new OccupationServiceJPA();
    }

    @Bean
    public String db(){return "";
    }

}
