package jpa.playground;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@Configuration
public class SpringClientPlay {
    public static void main(String[] args) {
        SpringApplication.run(SpringClientPlay.class);
    }

    @Bean
    public CommandLineRunner runnerSimple(){
        return args -> {
//            System.out.println("jpaaaaa");
            playground().play();
        };

    }

    @Bean
    public Playground playground(){return new Playground();}

}
