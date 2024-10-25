package controller;

import dao.CareerDAO;
import model.CareerInfo;

import java.time.LocalDate;
import java.util.List;

public class CareerController {
    private static CareerDAO careerDAO;

    static {
        careerDAO = new CareerDAO();
    }

   
    public static void addCareer(String username, String team, java.sql.Date startDate, java.sql.Date endDate) {
        careerDAO.addCareer(username, team, startDate, endDate);
    }

    public static List<CareerInfo> getCareerInfo(int playerId) {
        return careerDAO.getCareerInfo(playerId);
    }

    public static void updateStartDate(int careerId, LocalDate newStartDate) {
        careerDAO.updateStartDate(careerId, newStartDate);
    }

    public static void updateEndDate(int careerId, LocalDate newEndDate) {
        careerDAO.updateEndDate(careerId, newEndDate);
    }

    public static int getCareerId(String username, String teamName, java.sql.Date startDate) {
        return careerDAO.getCareerId(username, teamName, startDate);
    }

    public static String[] getTrophyName() {
        return careerDAO.getTrophyName();
    }
}
