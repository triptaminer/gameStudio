package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Player;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface PlayerService {
    /**
     * Adds new player to storage
     * @param player object to be added
     */
    void addPlayer(Player player) throws FileNotFoundException, SQLException;

    /**
     * Loads all players
     * @return List&lt;Player&gt;
     */
    List<Player> getPlayers() throws FileNotFoundException, SQLException;

    /**
     * returns Player object of specified player
     * @param name username of player
     * @return
     * @throws FileNotFoundException
     * @throws SQLException
     */
    Player getPlayerByUsername(String name) throws FileNotFoundException, SQLException;

    /**
     * deletes all scores from storage
     */
    void reset() throws FileNotFoundException, SQLException;

}
