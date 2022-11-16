package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.entity.Occupation;
import sk.tuke.gamestudio.services.*;

@SpringBootApplication
//@Configuration
public class SpringClient {

    //starts client AND server
//    public static void main(String[] args) {
//        SpringApplication.run(SpringClient.class);
//    }


    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
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
