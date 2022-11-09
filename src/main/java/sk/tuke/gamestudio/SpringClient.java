package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.services.GameStudioServices;

@SpringBootApplication
//@Configuration
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }

    //@Bean
    public CommandLineRunner runnerSimple(){
        return args -> {
            System.out.println("hell ouch");
        };

    }
    @Bean
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
}
