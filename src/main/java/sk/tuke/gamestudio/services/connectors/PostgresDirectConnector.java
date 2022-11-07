package sk.tuke.gamestudio.services.connectors;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class PostgresDirectConnector {

    private final Connection connection;
    private final String protocol = "jdbc:postgresql://";

    public PostgresDirectConnector(String user, String password, String host, int port, String database) {
        //this.connection = connection;
        String url = protocol + host + ":" + port + "/" + database;
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Unable to connect to database!");
            throw new RuntimeException(e);
        }
    }

    public PostgresDirectConnector() throws FileNotFoundException {
        File f = new File("psql.conf");
        Scanner reader = new Scanner(f);
        int i=0;

        while (reader.hasNextLine()) {
            String[] data = reader.nextLine().split(",");

            i++;
        }
        reader.close();

    }

    public int ddlQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }

    public int setQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeUpdate();
    }

    public int setQuery(String query,String[][] values) throws SQLException {
        if(values[0].length != query.replaceAll("[^?]*","").length() ){
            throw new RuntimeException("Incorrect pattern or number of items in query: "+query);
        }

        int result=0;
        PreparedStatement statement = connection.prepareStatement(query);
        for (String[] row : values) {
            int i=1;
            for (String col : row) {
                statement.setString(i, col);
                i++;
            }
            result+=statement.executeUpdate();
        }
        return result;
    }

    public ResultSet getQuery(String query, String[] values) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(query);

        int i=1;
        for (String col : values) {
            statement.setString(i, col);
            i++;
        }

        return statement.executeQuery();
    }
    public ResultSet getQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

}
