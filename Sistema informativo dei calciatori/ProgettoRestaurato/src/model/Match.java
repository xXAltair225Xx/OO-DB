package model;

import java.util.Date;

public class Match {
    private String homeTeam;
    private String guestTeam;
    private int homeGoals;
    private int guestGoals;
    private Date date;

    public Match(String homeTeam, String guestTeam, int homeGoals, int guestGoals, Date date) {
        this.homeTeam = homeTeam;
        this.guestTeam = guestTeam;
        this.homeGoals = homeGoals;
        this.guestGoals = guestGoals;
        this.date = date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public int getGuestGoals() {
        return guestGoals;
    }

    public Date getDate() {
        return date;
    }
}
