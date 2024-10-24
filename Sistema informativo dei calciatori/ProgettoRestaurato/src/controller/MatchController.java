package controller;

import dao.MatchDAO;
import model.Match;

import java.util.List;

public class MatchController {
    private static MatchDAO matchDAO;

    // Static block to initialize matchDAO
    static {
        matchDAO = new MatchDAO();
    }

    // Static methods to interact with matchDAO
    public static List<Match> getMatchesForCareerPeriod(int careerId) {
        return matchDAO.getMatchesForCareerPeriod(careerId);
    }

    public static int addNewMatch(int homeTeam, int guestTeam, java.sql.Date matchDate) {
        return matchDAO.addNewMatch(homeTeam, guestTeam, matchDate);
    }
}
