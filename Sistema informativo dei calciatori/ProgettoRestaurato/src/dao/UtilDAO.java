package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import db_connection.DBConnection;

public class UtilDAO {
    public static HashMap<String, String> loadCredentials(String username, String password, String userTag) {

        HashMap<String, String> credentials = new HashMap<>();

        String query = "";

        switch (userTag) {
            case "admin":
                query = "SELECT admin_username, admin_password FROM administrator WHERE admin_username = ? ";
                break;
            case "player":
                query = "SELECT player_username, player_password FROM player WHERE player_username = ? AND iscoach IS FALSE AND ismanager IS FALSE";
                break;
            case "coach":
                query = "SELECT player_username, player_password FROM player WHERE player_username = ? AND iscoach IS TRUE AND ismanager IS FALSE";
                break;
            case "manager":
                query = "SELECT player_username, player_password FROM player WHERE player_username = ? AND iscoach IS FALSE AND ismanager IS TRUE";
                break;
            default:
                throw new IllegalArgumentException("Invalid user tag: " + userTag);
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String userColumn = resultSet.getMetaData().getColumnLabel(1);
                    String passColumn = resultSet.getMetaData().getColumnLabel(2);
                    credentials.put(resultSet.getString(userColumn), resultSet.getString(passColumn));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return credentials;
    }
}
