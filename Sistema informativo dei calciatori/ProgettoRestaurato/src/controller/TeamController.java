package controller;

import dao.TeamDAO;

public class TeamController {
    private static TeamDAO teamDAO;

    // Static block to initialize teamDAO
    static {
        teamDAO = new TeamDAO();
    }

    // Static methods to interact with teamDAO
    public static void addTeamTrophy(int teamId, int trophy, java.sql.Date year) {
        teamDAO.addTeamTrophy(teamId, trophy, year);
    }

    public static String[] getTeamName() {
        return teamDAO.getTeamName();
    }

    public static String[] getTeamTrophyName() {
        return teamDAO.getTeamTrophyName();
    }

    public static int getTeamIdByName(String teamName) {
        return teamDAO.getTeamIdByName(teamName);
    }
}
