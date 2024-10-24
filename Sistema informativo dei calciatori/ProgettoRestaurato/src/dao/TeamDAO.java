package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db_connection.DBConnection;

public class TeamDAO {

    public static void addTeamTrophy(int team_id, int trophy, Date year) {
        try (Connection connection = DBConnection.getConnection()) {

            String sqlQuery = "INSERT INTO team_trophy (team_id, trophy_id, trophy_date) VALUES(?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

                preparedStatement.setInt(1, team_id);
                preparedStatement.setInt(2, trophy);
                preparedStatement.setDate(3, year);
                preparedStatement.addBatch();


                // Esegui tutte le operazioni di inserimento in una transazione
                connection.setAutoCommit(false);
                preparedStatement.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String[] getTeamName() {
        List<String> characteristic = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sqlQuery = "SELECT team_name FROM team";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {

                while (resultSet.next()) {
                    // Crea un oggetto Giocatore per ogni riga nel risultato

                    String role = resultSet.getString("team_name");



                    // Aggiungi il giocatore alla lista
                    characteristic.add(role);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return characteristic.toArray(new String[0]);
    }
    public static String[] getTeamTrophyName() {
        List<String> trophies = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sqlQuery = "SELECT trophy_name FROM trophy WHERE individual = FALSE";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {

                while (resultSet.next()) {
                    // Crea un oggetto Giocatore per ogni riga nel risultato

                    String role = resultSet.getString("trophy_name");



                    // Aggiungi il giocatore alla lista
                    trophies.add(role);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trophies.toArray(new String[0]);
    }
    public static int getTeamIdByName(String teamName) {
        int teamId = -1; // Valore di default nel caso in cui il nome della squadra non venga trovato
        try (Connection conn =DBConnection.getConnection()) {
            String query = "SELECT team_id FROM team WHERE team_name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, teamName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                teamId = rs.getInt("team_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamId;
    }

}
