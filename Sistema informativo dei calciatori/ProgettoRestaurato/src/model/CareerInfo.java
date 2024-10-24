package model;

import java.util.Date;

public class CareerInfo {
    private String teamName;
    private Date startDate;
    private Date endDate;
    private int goals;
    private int matches;
    private int trophies;

    public CareerInfo(String teamName, Date startDate, Date endDate, int goals, int matches, int trophies) {
        this.teamName = teamName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goals = goals;
        this.matches = matches;
        this.trophies = trophies;
    }

    public String getTeamName() {
        return teamName;
    }

    public java.sql.Date getStartDate() {
        return (java.sql.Date) startDate;
    }

    public java.sql.Date getEndDate() {
        return (java.sql.Date) endDate;
    }

    public int getGoals() {
        return goals;
    }

    public int getMatches() {
        return matches;
    }

    public int getTrophies() {
        return trophies;
    }


}
