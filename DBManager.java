import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
public class DBManager {
        private static HashMap<String, String> adminUserPasswordMap = new HashMap<>();
        private static HashMap<String, String> playerUserPasswordMap = new HashMap<>();

        private static JLabel profileLabel = new JLabel(" Mostra");

        public static void loadAdminCredentials() {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            // Database connection parameters
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String username = "postgres";
            String password = "Scognamiglio20";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                // Execute a simple SQL query
                String sqlQuery = "SELECT * FROM administrator";

                try (Statement statement = connection.createStatement()) {
                    ResultSet resultSet = statement.executeQuery(sqlQuery);

                    // Iterate through the result set and populate the HashMap
                    while (resultSet.next()) {
                        String columnUser = resultSet.getString("adminUsername");
                        String columnPassword = resultSet.getString("adminPassword");
                        adminUserPasswordMap.put(columnUser, columnPassword);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static HashMap<String, String> getAdminUserPasswordMap() {
            return adminUserPasswordMap;
        }

//Interazione con i dati di accesso del giocatore dal DB
       public static void loadPlayerCredentials() {
           try {
               Class.forName("org.postgresql.Driver");
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
               return;
           }

           // Database connection parameters
           String url = "jdbc:postgresql://localhost:5432/postgres";
           String username = "postgres";
           String password = "Scognamiglio20";

           try (Connection connection = DriverManager.getConnection(url, username, password)) {
               // Execute a simple SQL query
               String sqlQuery = "SELECT * FROM giocatore";

               try (Statement statement = connection.createStatement()) {
                   ResultSet resultSet = statement.executeQuery(sqlQuery);

                   // Iterate through the result set and populate the HashMap
                   while (resultSet.next()) {
                       String columnUser = resultSet.getString("login");
                       String columnPassword = resultSet.getString("password_giocatore");
                       playerUserPasswordMap.put(columnUser, columnPassword);
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

       public static HashMap<String, String> getPlayerUserPasswordMap() {
           return playerUserPasswordMap;
       }

//Metodo per modificare il profilo di un giocatore

       public static void modifyPlayerData(String username, String nuovoNome, String nuovoCognome,String nuovaPassword) {

           try {
               Class.forName("org.postgresql.Driver");
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
               return;
           }

           // Database connection parameters
           String url = "jdbc:postgresql://localhost:5432/postgres";
           String usernameDB = "postgres";
           String passwordDB = "Scognamiglio20";

           try (Connection connection = DriverManager.getConnection(url, usernameDB, passwordDB)) {
               // Esegui l'istruzione SQL di UPDATE
               String sqlQuery = "UPDATE giocatore SET nome = ?, cognome = ?, password_giocatore = ? WHERE login = ?";

               try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                   preparedStatement.setString(1, nuovoNome);
                   preparedStatement.setString(2, nuovoCognome);
                   preparedStatement.setString(3, nuovaPassword);
                   preparedStatement.setString(4, username);

                   int rowsUpdated = preparedStatement.executeUpdate();

                   if (rowsUpdated > 0) {
                       System.out.println("Modifica effettuata con successo!");
                   } else {
                       System.out.println("Nessuna modifica effettuata.");
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }

//Interazione con i dati dei giocatori presenti nel DB

           public static Object[][] getUserData () {
               List<Object[]> userDataList = new ArrayList<>();

               try {
                   Class.forName("org.postgresql.Driver");
               } catch (ClassNotFoundException e) {
                   e.printStackTrace();
                   return new Object[0][0];
               }

               // Database connection parameters
               String url = "jdbc:postgresql://localhost:5432/postgres";
               String username = "postgres";
               String password = "Scognamiglio20";

               try (Connection connection = DriverManager.getConnection(url, username, password)) {
                   String sqlQuery = "SELECT * FROM giocatore";  // Sostituisci 'your_table_name' con il nome reale della tabella

                   try (Statement statement = connection.createStatement()) {
                       ResultSet resultSet = statement.executeQuery(sqlQuery);

                       while (resultSet.next()) {
                           Object[] rowData = {
                                   resultSet.getString("nome"),
                                   resultSet.getString("cognome"),
                                   resultSet.getString("data_Nascita"),
                                   resultSet.getString("piede"),
                                   resultSet.getInt("trofei"),
                                   resultSet.getString("data_ritiro"),
                                   resultSet.getString("id_ruolo"),
                           };

                           userDataList.add(rowData);
                       }
                   }
               } catch (SQLException e) {
                   e.printStackTrace();
               }

               // Converte la lista in un array bidimensionale
               Object[][] userData = new Object[userDataList.size()][];
               userDataList.toArray(userData);
               return userData;
       }

//Interazione con il DB per aggiungere giocatori direttamente dal programma

    public static void addNewPlayer(String username,String password, String nome, String cognome, Date dataNascita, String piede) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String usernameDB = "postgres";
        String passwordDB = "Scognamiglio20";

        try (Connection connection = DriverManager.getConnection(url, usernameDB, passwordDB)) {
            // Esegui l'istruzione SQL di INSERT per aggiungere un nuovo giocatore
            String sqlQuery = "INSERT INTO giocatore (login,password_giocatore, nome, cognome, data_Nascita, piede) VALUES (?, ?, ?, ?, ?,?)";


            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2,password);
                preparedStatement.setString(3, nome);
                preparedStatement.setString(4, cognome);
                preparedStatement.setDate(5, dataNascita);
                preparedStatement.setString(6, piede);

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Giocatore aggiunto con successo!");
                } else {
                    System.out.println("Errore durante l'aggiunta del giocatore.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}




