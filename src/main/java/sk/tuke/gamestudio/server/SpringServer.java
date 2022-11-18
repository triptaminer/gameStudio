package sk.tuke.gamestudio.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.services.*;

@SpringBootApplication
@EntityScan(basePackages = "sk.tuke.gamestudio.entity, sk.tuke.gamestudio.services")

public class SpringServer {
    public static void main(String[] args) {
        SpringApplication.run(SpringServer.class);
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


}
