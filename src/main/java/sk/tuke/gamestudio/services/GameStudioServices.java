package sk.tuke.gamestudio.services;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.entity.*;
import sk.tuke.gamestudio.exceptions.ServiceException;
import sk.tuke.gamestudio.ui.ServiceUI;
import sk.tuke.gamestudio.ui.ServiceUIConsole;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;
import java.util.SimpleTimeZone;


public class GameStudioServices {

    private String gameName;

    @Autowired
    public ScoreService scoreService;

    @Autowired
    public CommentService commentService;

    @Autowired
    public RatingService ratingService;

    @Autowired
    public PlayerService playerService;
    @Autowired
    public CountryService countryService;
    @Autowired
    public OccupationService occupationService;

    @Autowired
    public HashService hashService;

    public ServiceUI serviceUI;

    public Player currentPlayer;

    @Autowired
    public Init init;

    public GameStudioServices() {
//        scoreService = new ScoreServiceJDBC();
//        scoreService = new ScoreServiceJPA();
//        commentService = new CommentServiceJPA();
//        ratingService = new RatingServiceJPA();

        serviceUI= new ServiceUIConsole(this);
        //this.init.status();
    }


    public String getUserName() {
        return currentPlayer.getName();
    }

//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void processScore(String gameName, int score) {
        try {
            scoreService.addScore(

                    new Score(gameName, this.currentPlayer, score, new Date())
            );
        } catch (FileNotFoundException e) {
            throw new ServiceException("Missing configuration file! "+e);
        } catch (SQLException e) {
            throw new ServiceException("Cannot execute SQL query! "+e);
        }
    }

    public boolean isCorrectPass(String password){

        String userHash=generateHash(currentPlayer.getName());

        String[] hxUser=generateHashes(currentPlayer.getName(),password);

        String[] hxDB=hashService.getHashes(userHash);

        //lets make it simple for now
        if(hxDB[0].equals(hxUser[0]) && hxDB[1].equals(hxUser[1])){
            //here should be some record about success login
            return true;
        }
        //here should be some record about failed login, counter, ...etc
        return false;
    }

    public void setPassword(String password){

        String h1=generateHash(currentPlayer.getName());
        String[] hx=generateHashes(currentPlayer.getName(), password);

        //Hash h=new Hash()
        hashService.addHash(new Hash(h1,hx[0],hx[1]));

    }

    public void setPassword(String user, String password){

        String h1=generateHash(user);
        String[] hx=generateHashes(user, password);

        //Hash h=new Hash()
        hashService.addHash(new Hash(h1,hx[0],hx[1]));

    }


    private String generateHash(){
        String serverSaltUsr="pomaranc";
        String hash;

        hash=currentPlayer.getName()+"."+currentPlayer.getName().length()+serverSaltUsr;
        System.out.println("pn: "+hash);
        hash=hashMe(hash);
        System.out.println("h1: "+hash);
        hash=hashMe(hash);
        System.out.println("h2: "+hash);

        return hash;
    }
    public String generateHash(String s){
        String serverSaltUsr="pomaranc";
        String hash;

        hash=s+"."+s.length()+serverSaltUsr;
        System.out.println("u: "+hash);
        hash=hashMe(hash);
        System.out.println("h1: "+hash);
        hash=hashMe(hash);
        System.out.println("h2: "+hash);
        return hash;
    }
    public String[] generateHashes(String user, String password){
        String serverSalt1="solSkodi";
        String serverSalt2="Hackerom";

        String h1=generateHash(user);

        String p1=password.substring(0,Math.round(password.length()/2));
        String p2=password.substring(Math.round(password.length()/2));

        System.out.println(p1+","+p2);

        String hashedP1=h1+p1+(user.length()-p1.length())+serverSalt1;
        int i=0;
        do{
            System.out.println("hp1 "+i+" before: "+ hashedP1);

            hashedP1=hashMe(hashedP1);

            System.out.println("hp1 "+i+" after: "+ hashedP1);
            i++;
        }while(i<p1.length());

        String hashedP2=p2+serverSalt2+h1+(user.length()+p2.length());
        i=0;
        do{
            System.out.println("hp2 "+i+" before: "+ hashedP2);

            hashedP2=hashMe(hashedP2);

            System.out.println("hp2 "+i+" after: "+ hashedP2);
            i++;
        }while(i<p2.length()+user.length());

        return new String[]{hashedP1,hashedP2};
    }

    private String hashMe(String s){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] encodedhash = digest.digest(
                s.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
