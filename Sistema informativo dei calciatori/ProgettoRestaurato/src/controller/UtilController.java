package controller;

import dao.UtilDAO;

import java.util.HashMap;

public class UtilController {

    private static UtilDAO utilDAO;

    // Static block to initialize utilDAO
    static {
        utilDAO = new UtilDAO();
    }

    // Static method using the initialized utilDAO
    public static HashMap<String, String> loadCredentials(String usernameColumn, String passwordColumn, String userTag) {
        return utilDAO.loadCredentials(usernameColumn, passwordColumn, userTag);
    }
}
