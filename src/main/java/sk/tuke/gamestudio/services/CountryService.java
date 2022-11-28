package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Country;
import sk.tuke.gamestudio.entity.Player;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface CountryService {
    /**
     * Adds new country to storage
     * @param country score object to be added
     */
    void addCountry(Country country) throws FileNotFoundException, SQLException;

    /**
     * Loads countrys
     * @return List&lt;Country&gt;
     */
    List<Country> getAllCountries() throws FileNotFoundException, SQLException;

    /**
     * Loads defined country
     * @param name of country object
     * @return Country
     */
    Country getCountry(String name) throws FileNotFoundException, SQLException;

    /**
     * deletes all countries from storage
     */
    void reset() throws FileNotFoundException, SQLException;

    Country getCountryById(int id);
}
