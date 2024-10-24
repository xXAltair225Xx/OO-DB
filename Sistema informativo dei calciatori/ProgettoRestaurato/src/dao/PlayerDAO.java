package dao;

import gui.CheckFrame;
import model.Player;
import db_connection.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerDAO {

    private UtilDAO utilDAO;
    public PlayerDAO() {
        this.utilDAO = new UtilDAO();
    }
    public HashMap<String, String> loadPlayerCredentials() {
        return utilDAO.loadCredentials("username", "password", "player");
    }
    public static int getPlayerIdByUsername(String username) {
        int playerId = -1; // Valore di default nel caso in cui non venga trovato alcun giocatore

        try (Connection connection = DBConnection.getConnection()) {
            String sqlQuery = "SELECT player_id FROM player WHERE player_username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, username);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        playerId = resultSet.getInt("player_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerId;
    }
    public String[] getPlayersByTeamId(int teamId) {
        List<String> playerNames = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT player.player_name, player.player_surname" +
                    " FROM player" +
                    " LEFT JOIN career ON player.player_id = career.player_id" +
                    " WHERE team_id = ? AND end_date IS NULL";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, teamId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String playerName = rs.getString("player_name");
                String playerSurname = rs.getString("player_surname");
                System.out.println(playerName + " " + playerSurname);
                playerNames.add(playerName + " " + playerSurname);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerNames.toArray(new String[0]);
    }
    public int getPlayerIdByFullName(String fullName) {
        int playerId = -1; // Valore di default nel caso in cui il giocatore non venga trovato
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT player_id FROM player WHERE CONCAT(player_name, ' ', player_surname) = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, fullName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                playerId = rs.getInt("player_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return playerId;
    }
    public void addPlayerRole(int playerId, List<Integer> roles) {
        try (Connection connection = DBConnection.getConnection()) {
            // Elimina tutti i ruoli associati al giocatore
            String deleteTrophiesQuery = "DELETE FROM player_role WHERE player_id = ?";
            try (PreparedStatement deleteTrophiesStatement = connection.prepareStatement(deleteTrophiesQuery)) {
                deleteTrophiesStatement.setInt(1, playerId);
                deleteTrophiesStatement.executeUpdate();
            }

            // Aggiungi i nuovi ruoli nella tabella player_role
            String addRolesQuery = "INSERT INTO player_role (player_id, role_id) VALUES (?, ?)";
            try (PreparedStatement addRolesStatement = connection.prepareStatement(addRolesQuery)) {
                for (int role : roles) {
                    addRolesStatement.setInt(1, playerId);
                    addRolesStatement.setInt(2, role);
                    addRolesStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addPlayerTrophy(int player_id, int trophy, Date year) {
        try (Connection connection = DBConnection.getConnection()) {

            String sqlQuery = "INSERT INTO player_trophy (player_id, trophy_id, trophy_year) VALUES(?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

                preparedStatement.setInt(1, player_id);
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
    public void addNewPlayer(String usernameNewPlayer, String passwordNewPlayer, String nome, String cognome, Date dataNascita, String piede, List<Integer> roles) {
        try (Connection connection = DBConnection.getConnection()) {
            // Esegui l'istruzione SQL di INSERT per aggiungere un nuovo giocatore
            String sqlQuery = "INSERT INTO player (player_username, player_password, player_name, player_surname, birth_date, foot) VALUES (?, ? , ? , ? , ? , ?)";


            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, usernameNewPlayer);
                preparedStatement.setString(2, passwordNewPlayer);
                preparedStatement.setString(3, nome);
                preparedStatement.setString(4, cognome);
                preparedStatement.setDate(5, dataNascita);
                preparedStatement.setString(6, piede);


                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    // Recupera l'ID del giocatore appena inserito
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int playerId = generatedKeys.getInt(1);

                            // Aggiungi i ruoli nella tabella player_roles

                            addPlayerRole(playerId, roles);
                            new CheckFrame("Success");

                            System.out.println("Giocatore aggiunto con successo!");
                        } else {
                            System.out.println("Errore durante il recupero dell'ID del giocatore.");
                        }
                    }
                } else {
                    System.out.println("Errore durante l'aggiunta del giocatore.");
                }
            }
        } catch (SQLException e) {
            new CheckFrame("This Username is already used");
        }
    }
    public void addPlayerMatchStats(int playerId, int teamId, int matchId, int scoredGoals, int roleId) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO player_match_stats (player_id, team_id, match_id, scored_goals, role_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, playerId);
            pstmt.setInt(2, teamId);
            pstmt.setInt(3, matchId);
            pstmt.setInt(4, scoredGoals);
            pstmt.setInt(5, roleId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public String[] getCharacteristicName() {
        List<String> characteristic = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sqlQuery = "SELECT characteristic FROM characteristic";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {

                while (resultSet.next()) {
                    // Crea un oggetto Giocatore per ogni riga nel risultato

                    String role = resultSet.getString("characteristic");



                    // Aggiungi il giocatore alla lista
                    characteristic.add(role);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return characteristic.toArray(new String[0]);
    }

    public static List<Player> getPlayer() {
        List<Player> players = new ArrayList<>();


        String query = "SELECT player.player_id, player.player_username, player.player_password, player.player_name, player.player_surname, " +
                "player.birth_date, player.retirement_date, player.foot, array_agg(DISTINCT roleList.role_name) AS roles , array_agg(DISTINCT characteristic.characteristic) AS characteristic, player.totalscoredgoal, player.goals_conceded, player.trophies_won " +
                "FROM player " +
                "LEFT JOIN player_role ON player.player_id = player_role.player_id " +
                "LEFT JOIN roleList ON player_role.role_id = roleList.role_id " +
                "LEFT JOIN characteristic_player ON player.player_id = characteristic_player.player_id " +
                "LEFT JOIN characteristic ON characteristic_player.characteristic_id = characteristic.characteristic_id " +
                "GROUP BY player.player_id ";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            // Execute a simple SQL query

            while (resultSet.next()) {
                // Crea un oggetto Giocatore per ogni riga nel risultato
                Player player = new Player(
                        resultSet.getInt("player_id"),
                        resultSet.getString("player_username"),
                        resultSet.getString("player_password"),
                        resultSet.getString("player_name"),
                        resultSet.getString("player_surname"),
                        resultSet.getDate("birth_date"),
                        resultSet.getDate("retirement_date"),
                        resultSet.getString("foot"),
                        (String[]) resultSet.getArray("roles").getArray(),
                        (String[]) resultSet.getArray("characteristic").getArray(),
                        resultSet.getInt("totalscoredgoal"),
                        resultSet.getInt("goals_conceded"),
                        resultSet.getInt("trophies_won")
                );
                // Aggiungi il giocatore alla lista
                players.add(player);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return players;
    }
    public void playerRetirement(String playerUsername) {
        try (Connection connection = DBConnection.getConnection()) {
            String sqlQuery = "UPDATE player SET retirement_date = CURRENT_TIMESTAMP WHERE player_username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                // Set the value for the placeholder
                preparedStatement.setString(1, playerUsername);

                // Execute the update
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deletePlayer(int player_id){

        try (Connection connection = DBConnection.getConnection()) {
            String sqlQuery =
                    "DELETE FROM player_trophy WHERE player_id = ?;"+
                            " DELETE FROM player_match_stats WHERE player_id = ?;" +
                            " DELETE FROM characteristic_player WHERE player_id = ?;" +
                            " DELETE FROM player_role WHERE player_id = ?;" +
                            " DELETE FROM career WHERE player_id = ?;" +
                            " DELETE FROM player WHERE player_id = ?;" ;

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery) ) {
                // Set the value for the placeholder
                preparedStatement.setInt(1, player_id);
                preparedStatement.setInt(2, player_id);
                preparedStatement.setInt(3, player_id);
                preparedStatement.setInt(4, player_id);
                preparedStatement.setInt(5, player_id);
                preparedStatement.setInt(6,player_id);
                // Execute the update
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public Date getRetirementDate(String username){

        String sqlQuery = "SELECT retirement_date FROM player WHERE player_username = ?";
        try (Connection connection = DBConnection.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()){
                    return resultSet.getDate("retirement_date");
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public String[] getRolesName() {
        List<String> roles = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sqlQuery = "SELECT role_name FROM rolelist";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {

                while (resultSet.next()) {
                    // Crea un oggetto Giocatore per ogni riga nel risultato

                    String role = resultSet.getString("role_name");



                    // Aggiungi il giocatore alla lista
                    roles.add(role);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles.toArray(new String[0]);
    }
    public void modifyPlayerData(String username, String campo, String nuovo) {
        String sqlQuery = "";
        switch (campo) {
            case "Name":
                sqlQuery = "UPDATE player SET player_name = ? WHERE player_username = ?";
                break;
            case "Surname":
                sqlQuery = "UPDATE player SET player_surname = ? WHERE player_username = ?";
                break;
            case "Password":
                sqlQuery = "UPDATE player SET player_password = ? WHERE player_username = ?";
                break;
            case "Foot":
                sqlQuery = "UPDATE player SET foot = ? WHERE player_username = ?";
                break;

            // Aggiungi altri casi per gli altri campi da modificare
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, nuovo);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate(); // Esegui la query per effettuare la modifica nel database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int getRoleIdByPlayerId(int playerId) {
        int roleId = -1; // Valore di default nel caso in cui il ruolo non venga trovato
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT role_id FROM player_role WHERE player_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, playerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                roleId = rs.getInt("role_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roleId;
    }
    public static void deletePlayerRoleOrCharacteristic(int playerId, String campo) {
        try (Connection connection = DBConnection.getConnection()) {

            String sqlQuery = null;
            if(campo.equals("Role")){
                sqlQuery = "DELETE FROM player_role WHERE player_id = ?";
            } else {
                sqlQuery = "DELETE FROM characteristic_player WHERE player_id = ?";
            }
            try (PreparedStatement psmt = connection.prepareStatement(sqlQuery)) {
                psmt.setInt(1, playerId);
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void modifyPlayerCharacteristics(int playerId, int characteristics) {
        try (Connection connection = DBConnection.getConnection()) {
            String addRolesQuery = "INSERT INTO characteristic_player(player_id, characteristic_id) VALUES (?, ?)";
            try (PreparedStatement addRolesStatement = connection.prepareStatement(addRolesQuery)) {
                addRolesStatement.setInt(1, playerId);
                addRolesStatement.setInt(2, characteristics);
                addRolesStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


