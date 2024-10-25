package dao;

import model.Match;
import db_connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {

    //Recupera le partite giocate durante una determinata militanza
    public static List<Match> getMatchesForCareerPeriod(int careerId) {
        List<Match> matches = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            String query = "SELECT m.*, home_team.team_name AS home_team_name, guest_team.team_name AS guest_team_name " +
                    "FROM match m " +
                    "INNER JOIN career c ON (m.home_team_id = c.team_id OR m.guest_team_id = c.team_id) " +
                    "INNER JOIN team home_team ON m.home_team_id = home_team.team_id " +
                    "INNER JOIN team guest_team ON m.guest_team_id = guest_team.team_id " +
                    "INNER JOIN player_match_stats pms ON m.match_id = pms.match_id " +
                    "WHERE c.career_id = ? " +
                    "AND pms.player_id = (SELECT player_id FROM career WHERE career_id = ?)";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, careerId);
            pstmt.setInt(2, careerId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String homeTeam = rs.getString("home_team_name");
                String guestTeam = rs.getString("guest_team_name");
                int homeGoals = rs.getInt("home_goals_scored");
                int guestGoals = rs.getInt("guest_goals_scored");
                Date matchDate = rs.getDate("date_match");
                Match match = new Match(homeTeam, guestTeam, homeGoals, guestGoals, matchDate);
                matches.add(match);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return matches;
    }

    //Aggiunge una nuova partita
    public static int addNewMatch(int homeTeam, int guestTeam, Date matchDate) {
        int matchId = -1; // Inizializza il matchId a un valore di default

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO match (home_team_id, guest_team_id, date_match) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, homeTeam);
            pstmt.setInt(2, guestTeam);
            pstmt.setDate(3, new java.sql.Date(matchDate.getTime()));
            pstmt.executeUpdate();

            // Ottieni l'ID generato per il nuovo match
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                matchId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return matchId;
    }

}
