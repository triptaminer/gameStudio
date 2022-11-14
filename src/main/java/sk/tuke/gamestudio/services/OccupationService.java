package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Occupation;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface OccupationService {
    /**
     * Adds new occupation to storage
     * @param occupation score object to be added
     */
    void addOccupation(Occupation occupation) throws FileNotFoundException, SQLException;

    /**
     * Loads occupations
     * @return List&lt;Occupation&gt;
     */
    List<Occupation> getAllOccupations() throws FileNotFoundException, SQLException;

    /**
     * Loads defined occupation
     * @param name of occupation object
     * @return Occupation
     */
    Occupation getOccupation(String name) throws FileNotFoundException, SQLException;

    /**
     * deletes all occupations from storage
     */
    void reset() throws FileNotFoundException, SQLException;

}
