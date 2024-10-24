package controller;

import dao.PlayerDAO;
import model.Player;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public class PlayerController {

    static PlayerDAO playerDAO;

    // Static block to initialize playerDAO
    static {
        playerDAO = new PlayerDAO();
    }

    public static HashMap<String, String> loadPlayerCredentials() {
        return playerDAO.loadPlayerCredentials();
    }

    public static int getPlayerIdByUsername(String username) {
        return playerDAO.getPlayerIdByUsername(username);
    }

    public static String[] getPlayersByTeamId(int teamId) {
        return playerDAO.getPlayersByTeamId(teamId);
    }

    public static int getPlayerIdByFullName(String fullName) {
        return playerDAO.getPlayerIdByFullName(fullName);
    }

    public static void addPlayerTrophy(int playerId, int trophy, Date year) {
        playerDAO.addPlayerTrophy(playerId, trophy, year);
    }

    public static void addNewPlayer(String usernameNewPlayer, String passwordNewPlayer, String nome, String cognome, Date dataNascita, String piede, List<Integer> roles) {
        playerDAO.addNewPlayer(usernameNewPlayer, passwordNewPlayer, nome, cognome, dataNascita, piede, roles);
    }

    public static void addPlayerMatchStats(int playerId, int teamId, int matchId, int scoredGoals, int roleId) {
        playerDAO.addPlayerMatchStats(playerId, teamId, matchId, scoredGoals, roleId);
    }

    public static String[] getCharacteristicName() {
        return playerDAO.getCharacteristicName();
    }

    public static List<Player> getPlayers() {
        return playerDAO.getPlayer();
    }

    public static void playerRetirement(String playerUsername) {
        playerDAO.playerRetirement(playerUsername);
    }

    public static void deletePlayer(int playerId) {
        playerDAO.deletePlayer(playerId);
    }

    public static Date getRetirementDate(String username) {
        return playerDAO.getRetirementDate(username);
    }

    public static String[] getRolesName() {
        return playerDAO.getRolesName();
    }

    public static void modifyPlayerData(String username, String campo, String nuovo) {
        playerDAO.modifyPlayerData(username, campo, nuovo);
    }

    public static int getRoleIdByPlayerId(int playerId) {
        return playerDAO.getRoleIdByPlayerId(playerId);
    }

    public static void deletePlayerRoleOrCharacteristic(int playerId, String campo) {
        playerDAO.deletePlayerRoleOrCharacteristic(playerId, campo);
    }

    public static void modifyPlayerCharacteristics(int playerId, int characteristics) {
        playerDAO.modifyPlayerCharacteristics(playerId, characteristics);
    }

    public static void modifyPlayerRole(int playerId, List<Integer> roles) {
        playerDAO.addPlayerRole(playerId, roles);
    }
}
