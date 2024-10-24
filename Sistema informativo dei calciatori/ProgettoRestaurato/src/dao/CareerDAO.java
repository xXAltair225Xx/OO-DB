package dao;

import controller.PlayerController;
import controller.TeamController;
import model.CareerInfo;
import db_connection.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CareerDAO {

    public static List<CareerInfo> getCareerInfo(int playerId) {
        List<CareerInfo> careerInfoList = new ArrayList<>();

        try (Connection connection =DBConnection.getConnection()) {
            String sqlQuery = "SELECT team.team_name, career.start_date, career.end_date, career.goal_scored, career.match_played, career.trophies_won " +
                    "FROM career " +
                    "JOIN team ON career.team_id = team.team_id " +
                    "WHERE career.player_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setInt(1, playerId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String teamName = resultSet.getString("team_name");
                        Date startDate = resultSet.getDate("start_date");
                        Date endDate = resultSet.getDate("end_date");
                        int goals = resultSet.getInt("goal_scored");
                        int matches = resultSet.getInt("match_played");
                        int trophies = resultSet.getInt("trophies_won");

                        CareerInfo careerInfo = new CareerInfo(teamName, startDate, endDate, goals, matches, trophies);
                        careerInfoList.add(careerInfo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return careerInfoList;
    }

    public static void addCareer(String username, String team, Date startDatePicker, Date endDatePicker) {
        int playerId = PlayerController.getPlayerIdByUsername(username);
        int teamId = TeamController.getTeamIdByName(team);

        try (Connection conn =DBConnection.getConnection()) {
            String query;
            if (endDatePicker != null) {
                query = "INSERT INTO career (player_id, team_id, start_date, end_date) " +
                        "VALUES (?, ?, ?, ?)";
            } else {
                query = "INSERT INTO career (player_id, team_id, start_date) " +
                        "VALUES (?, ?, ?)";
            }
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, playerId);
                pstmt.setInt(2, teamId);
                pstmt.setDate(3, new Date(startDatePicker.getTime()));
                if (endDatePicker != null) {
                    pstmt.setDate(4, new Date(endDatePicker.getTime()));
                }
                pstmt.executeUpdate();
            }
            System.out.println("Career added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStartDate(int careerId, LocalDate newStartDate) {
        try (Connection conn =DBConnection.getConnection()) {
            String query = "UPDATE career SET start_date = ? WHERE career_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, java.sql.Date.valueOf(newStartDate));
                pstmt.setInt(2, careerId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEndDate(int careerId, LocalDate newEndDate) {
        try (Connection conn =DBConnection.getConnection()) {
            String query = "UPDATE career SET end_date = ? WHERE career_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDate(1, java.sql.Date.valueOf(newEndDate));
                pstmt.setInt(2, careerId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getCareerId(String username, String teamName, Date startDate) {
        int careerId = -1; // Valore predefinito nel caso in cui non venga trovata una corrispondenza

        String query = "SELECT career_id FROM career WHERE player_id = ? AND team_id = ? AND start_date = ? " ;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Imposta i parametri della query con i dettagli della carriera
            pstmt.setInt(1, PlayerController.getPlayerIdByUsername(username));
            pstmt.setInt(2, TeamController.getTeamIdByName(teamName));
            pstmt.setDate(3, new java.sql.Date(startDate.getTime()));

            // Esegui la query
            ResultSet rs = pstmt.executeQuery();

            // Se la query restituisce un risultato, ottieni l'ID della carriera corrispondente
            if (rs.next()) {
                careerId = rs.getInt("career_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return careerId;
    }

    public  String[] getTrophyName() {
        List<String> trophies = new ArrayList<>();
        try (Connection connection =DBConnection.getConnection()) {
            String sqlQuery = "SELECT trophy_name FROM trophy WHERE individual = TRUE";

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


}
