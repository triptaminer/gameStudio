package sk.tuke.gamestudio.services;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Init {

    //@Autowired

//    @Autowired
//    GameStudioServices gss;

    @Autowired
    CountryService countryService;
    @Autowired
    OccupationService occupationService;

    @Autowired
    PlayerService playerService;

    @Autowired
    CommentService commentService;
    @Autowired
    ScoreService scoreService;
    @Autowired
    RatingService ratingService;

    @Autowired
    HashService hashService;

    public void status() {
        System.out.println("Checking DB...");
//        gss = new GameStudioServices();
//        gss.countryService=new CountryServiceJPA();

        Country test = null;
        try {
            test = countryService.getCountry("Afghanistan");
        } catch (FileNotFoundException | SQLException e) {
            System.out.println("Maybe there are some issues with DB. Please check if DB is prepared for MY configuration.");
        }

        if (test == null) {
            try {
                fillDB();
            } catch (SQLException | FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Done, enjoy!");
        }

    }

    private void fillDB() throws SQLException, FileNotFoundException {


        System.out.println("Adding countries...");
        File f = new File("./src/main/resources/inits/countries");
        Scanner myReader = new Scanner(f);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data != null) {
                countryService.addCountry(new Country(data));
                System.out.println("Added " + data);
            }
        }

        System.out.println("Adding occupations...");
        f = new File("./src/main/resources/inits/occupations");
        myReader = new Scanner(f);

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data != null) {
                occupationService.addOccupation(new Occupation(data));
                System.out.println("Added " + data);
            }
        }

        System.out.println("Adding few users...");

        String userName,h1;
        String[] hx;
        GameStudioServices gss=new GameStudioServices();

        userName="Admin";
        playerService.addPlayer(new Player(
                userName,
                "Admin account",
                countryService.getCountry("Slovakia"),
                occupationService.getOccupation("other")
        ));
        //Giving Admin rights
        gss.currentPlayer=playerService.getPlayerByUsername(userName);
        gss.currentPlayer.setPrivilege(4);
        h1=gss.generateHash(userName);
        hx=gss.generateHashes(userName, "IAm(G)root!");
        hashService.addHash(new Hash(h1,hx[0],hx[1]));

        userName="Moderator";
        playerService.addPlayer(new Player(
                userName,
                "MOD account",
                countryService.getCountry("Slovakia"),
                occupationService.getOccupation("other")
        ));
        playerService.getPlayerByUsername(userName).setPrivilege(2);
        gss.currentPlayer=playerService.getPlayerByUsername(userName);
        //Giving MOD rights
        gss.currentPlayer.setPrivilege(2);
        h1=gss.generateHash(userName);
        hx=gss.generateHashes(userName, "ZASAheslo?");
        hashService.addHash(new Hash(h1,hx[0],hx[1]));

        userName="viki";
        playerService.addPlayer(new Player(
                userName,
                "creator",
                countryService.getCountry("Slovakia"),
                occupationService.getOccupation("employee")
        ));
        gss.currentPlayer=playerService.getPlayerByUsername(userName);
        h1=gss.generateHash(userName);
        hx=gss.generateHashes(userName, "password123456qwertyLOVEgod");
        hashService.addHash(new Hash(h1,hx[0],hx[1]));

        userName="Guest";
        playerService.addPlayer(new Player(
                userName,
                "account for testing",
                countryService.getCountry("Slovakia"),
                occupationService.getOccupation("invalid")
        ));
        gss.currentPlayer=playerService.getPlayerByUsername(userName);
        h1=gss.generateHash(userName);
        hx=gss.generateHashes(userName, "heslo");
        hashService.addHash(new Hash(h1,hx[0],hx[1]));



        System.out.println("Added users: Admin, viki, Guest. For guest use password: 'heslo' :)");
        System.out.println("Adding some content...");

        scoreService.addScore(new Score("Mines",playerService.getPlayerByUsername("Admin"),1,new Date(0)));
        scoreService.addScore(new Score("Lights",playerService.getPlayerByUsername("Admin"),1,new Date(0)));
        scoreService.addScore(new Score("Tiles",playerService.getPlayerByUsername("Admin"),1,new Date(0)));
        scoreService.addScore(new Score("Mines",playerService.getPlayerByUsername("viki"),2,new Date(4546)));
        scoreService.addScore(new Score("Lights",playerService.getPlayerByUsername("viki"),2,new Date(54646)));
        scoreService.addScore(new Score("Tiles",playerService.getPlayerByUsername("viki"),2,new Date(78787)));
        System.out.println("Scores: 6 OK");

        ratingService.addRating(new Rating("Mines",playerService.getPlayerByUsername("Admin"),5,new Date(0)));
        ratingService.addRating(new Rating("Lights",playerService.getPlayerByUsername("Admin"),5,new Date(0)));
        ratingService.addRating(new Rating("Tiles",playerService.getPlayerByUsername("Admin"),5,new Date(0)));
        ratingService.addRating(new Rating("Mines",playerService.getPlayerByUsername("viki"),4,new Date(12)));
        ratingService.addRating(new Rating("Lights",playerService.getPlayerByUsername("viki"),3,new Date(12)));
        ratingService.addRating(new Rating("Tiles",playerService.getPlayerByUsername("viki"),2,new Date(12)));
        System.out.println("Ratings: 6 OK");

        commentService.addComment(new Comment("Mines",playerService.getPlayerByUsername("Admin"),"one initial comment",new Date(0)));
        commentService.addComment(new Comment("Lights",playerService.getPlayerByUsername("Admin"),"one initial comment",new Date(0)));
        commentService.addComment(new Comment("Tiles",playerService.getPlayerByUsername("Admin"),"one initial comment",new Date(0)));
        String lorem="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Risus pretium quam vulputate dignissim suspendisse. Nam aliquam sem et tortor consequat id.";
        commentService.addComment(new Comment("Mines",playerService.getPlayerByUsername("viki"),lorem,new Date(45645)));
        commentService.addComment(new Comment("Lights",playerService.getPlayerByUsername("viki"),lorem,new Date(45545)));
        commentService.addComment(new Comment("Tiles",playerService.getPlayerByUsername("viki"),lorem,new Date(87545)));
        System.out.println("Comments: 6 OK");


    }
}
