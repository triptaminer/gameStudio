package sk.tuke.gamestudio.services.connectors;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class PostgresDirectConnector {

    private final Connection connection;
    private final String protocol = "jdbc:postgresql://";

    /**
     * customizable constructor for specific cases. please prefer empty one with config file!
     * @param user
     * @param password
     * @param host
     * @param port
     * @param database
     */
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

    /**
     * Empty constructor for loading configuration from file
     * @throws FileNotFoundException
     */
    public PostgresDirectConnector() throws FileNotFoundException {
        Properties props = new Files().getProp();
        String user = props.get("JDBC_USER").toString();
        String password = props.get("JDBC_PASSWORD").toString();
        String host = props.get("JDBC_HOST").toString();
        int port = Integer.parseInt(props.get("JDBC_PORT").toString());
        String database = props.get("JDBC_DATABASE").toString();

        String url = protocol + host + ":" + port + "/" + database;
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Unable to connect to database!");
            throw new RuntimeException(e);
        }

    }

    public int ddlQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(query);
    }

    public int setQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeUpdate();
    }

    public int setQuery(String query, Object[][] values) throws SQLException {
        if (values[0].length != query.replaceAll("[^?]*", "").length()) {
            throw new RuntimeException("Incorrect pattern or number of items in query: " + query);
        }

//        System.out.println("dbg: "+query);
        int result = 0;
        PreparedStatement statement = connection.prepareStatement(query);
        for (Object[] row : values) {
            int i = 1;
            for (Object col : row) {

                setByType(statement, i, col);

//                System.out.println("dbg: "+i+":"+col);
                i++;
            }
            result += statement.executeUpdate();
        }
        return result;
    }


    public ResultSet getQuery(String query, Object[] values) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(query);

        int i = 1;
        for (Object col : values) {

            setByType(statement, i, col);

            i++;
        }

        return statement.executeQuery();
    }

    public ResultSet getQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    private static void setByType(PreparedStatement statement, int i, Object col) throws SQLException {
        //TODO here we need more types in future!!!
        if(col instanceof String)
            statement.setString(i, col.toString());
        if(col instanceof Integer)
            statement.setInt(i, Integer.parseInt(col.toString()));
        if(col instanceof Timestamp)
            statement.setTimestamp(i, Timestamp.valueOf(col.toString()));
    }

}
