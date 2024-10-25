package gui;

import controller.CareerController;
import controller.MatchController;
import controller.PlayerController;
import model.CareerInfo;
import model.Match;
import model.Player;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlayerProfileFrame implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JPanel tablePanel;
    private JPanel buttonPanel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel birthDateLabel;
    private JLabel footLabel;
    private JLabel roleLabel;
    private JLabel characteristic;
    private JLabel individualTrophy;
    private JXTable careerTable;
    private JButton backButton;
    private JButton playerModifyButton;
    private JButton deletePlayerButton;
    private static final String[] careerColumnNames = {"Team", "Start Date", "End Date", "Goals", "Matches", "Trophies"};
    private static Object[][] careerData;
    private static JScrollPane careerScrollPane;

    public PlayerProfileFrame(Player player, List<CareerInfo> careerInfoList) {

        careerData = convertCareerInfoToData(careerInfoList);

        frame = new JFrame("Player details");
        panel = new JPanel(new GridLayout(4, 2));
        tablePanel = new JPanel(new GridLayout(1,1));
        buttonPanel = new JPanel(new GridLayout(1,3));
        nameLabel = new JLabel("Name: " + player.getName());
        surnameLabel = new JLabel("Surname: " + player.getSurname());
        birthDateLabel = new JLabel("Birth date: " + formatDate(player.getBirthDate()));
        footLabel = new JLabel("Foot: " + player.getFoot());
        characteristic = new JLabel("Characteristic: " + player.getPlayer_characteristic());
        individualTrophy = new JLabel("Individual trophies: "+ player.getIndividualTrophy());
        roleLabel = new JLabel("Role: " + player.getPlayer_role());
        careerTable = new JXTable(careerData, careerColumnNames);
        backButton = new JButton("Go back");
        playerModifyButton = new JButton("Modify");
        deletePlayerButton = new JButton("Delete");
        careerScrollPane = new JScrollPane(careerTable);

        frame.setSize(600, 400);  // Dimensioni aumentate per adattarsi alla nuova tabella
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        careerTable.setEditable(false);


        panel.add(nameLabel);
        panel.add(surnameLabel);
        panel.add(birthDateLabel);
        panel.add(footLabel);
        panel.add(characteristic);
        panel.add(roleLabel);
        panel.add(individualTrophy);



        if(WelcomeFrame.role.equals("Admin")){
            buttonPanel.add(playerModifyButton);
            buttonPanel.add(deletePlayerButton);

            playerModifyButton.addActionListener(new ActionListener() {
                @Override

                public void actionPerformed(ActionEvent e) {
                    // Chiudi la finestra di login e apri la schermata iniziale
                    frame.dispose();
                    new PlayerModifyFrame(player.getUsername());
                }
            });

            deletePlayerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    PlayerController.deletePlayer(player.getPlayer_id());
                    frame.dispose();
                    List<Player> userData = PlayerController.getPlayers();
                    new MainFrame(userData);
                    new CheckFrame("Success");
                }
            });

        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                List<Player> userData = PlayerController.getPlayers();
                new MainFrame(userData);

            }
        });




        // Aggiungi un listener di selezione alla tabella
        careerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = careerTable.getSelectedRow();
                if (selectedRow != -1) {

                    String username = player.getUsername();
                    String teamName = (String) careerTable.getValueAt(selectedRow, 0);
                    String startDateString = (String) careerTable.getValueAt(selectedRow, 1);
                    java.sql.Date startDate = parseDate(startDateString);

                    int careerId = CareerController.getCareerId(username, teamName, startDate);
                    List<Match> matchesList = MatchController.getMatchesForCareerPeriod(careerId);
                    if (careerId != -1) {
                        new MatchesPlayedFrame(matchesList);
                    }
                }
            }
        });




        tablePanel.add(careerScrollPane);
        buttonPanel.add(backButton);
        frame.setLayout(new GridLayout(3,1));
        frame.add(panel);
        frame.add(tablePanel);
        frame.add(buttonPanel);
        frame.setVisible(true);

    }

    public String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        } else {
            return " ";
        }
    }


    private Object[][] convertCareerInfoToData(List<CareerInfo> careerInfoList) {
        // Converte la lista di CareerInfo in un array bidimensionale per la tabella
        Object[][] data = new Object[careerInfoList.size()][6];
        for (int i = 0; i < careerInfoList.size(); i++) {
            CareerInfo careerInfo = careerInfoList.get(i);
            data[i][0] = careerInfo.getTeamName();
            data[i][1] = formatDate(careerInfo.getStartDate());
            data[i][2] = formatDate(careerInfo.getEndDate());
            data[i][3] = careerInfo.getGoals();
            data[i][4] = careerInfo.getMatches();
            data[i][5] = careerInfo.getTrophies();
        }
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            frame.dispose();
            List<Player> userData = PlayerController.getPlayers();
        }
    }

    private java.sql.Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Parse la data e poi la converte in java.sql.Date
            Date utilDate = dateFormat.parse(dateString);
            return new java.sql.Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // In caso di errore nella conversione, restituisci null
        }
    }


}

