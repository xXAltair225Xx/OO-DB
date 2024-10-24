package gui;

import controller.CareerController;
import controller.PlayerController;
import controller.TeamController;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

class SelectFormationFrame {
    private JFrame frame;
    private JPanel panel;
    private JLabel homeTeamLabel;
    private JLabel guestTeamLabel;
    private JComboBox<String>[] homePlayerComboBoxes;
    private JComboBox<String>[] guestPlayerComboBoxes;
    private JTextField[] homeGoalFields;
    private JTextField[] guestGoalFields;
    private JButton submitButton;

    public SelectFormationFrame(String homeTeam, String guestTeam, int matchId) {
        frame = new JFrame("Select Formation");
        panel = new JPanel(new GridLayout(26, 3));

        homeTeamLabel = new JLabel("Home Team: " + homeTeam);
        guestTeamLabel = new JLabel("Guest Team: " + guestTeam);
        homePlayerComboBoxes = new JComboBox[11];
        guestPlayerComboBoxes = new JComboBox[11];
        homeGoalFields = new JTextField[11];
        guestGoalFields = new JTextField[11];
        submitButton = new JButton("Submit");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        panel.add(homeTeamLabel);
        panel.add(new JLabel());
        panel.add(guestTeamLabel);
        panel.add(new JLabel());

        panel.add(new JLabel());
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        panel.add(new JLabel("Player"));
        panel.add(new JLabel("Goals"));

        String[] homePlayersList = PlayerController.getPlayersByTeamId(TeamController.getTeamIdByName(homeTeam));
        String[] guestPlayersList = PlayerController.getPlayersByTeamId(TeamController.getTeamIdByName(guestTeam));

        for (int i = 0; i < 11; i++) {
            homePlayerComboBoxes[i] = new JComboBox<>(homePlayersList);
            guestPlayerComboBoxes[i] = new JComboBox<>(guestPlayersList);
            homeGoalFields[i] = new JTextField("0");
            guestGoalFields[i] = new JTextField("0");

            // Inizializza le combobox con un giocatore diverso in posizione i
            homePlayerComboBoxes[i].setSelectedIndex(i);
            guestPlayerComboBoxes[i].setSelectedIndex(i);

            panel.add(new JLabel("Home Player " + (i + 1)));
            panel.add(homePlayerComboBoxes[i]);
            panel.add(homeGoalFields[i]);

            panel.add(new JLabel("Guest Player " + (i + 1)));
            panel.add(guestPlayerComboBoxes[i]);
            panel.add(guestGoalFields[i]);
        }

        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Itera sui giocatori di casa
                for (int i = 0; i < 11; i++) {
                    int homePlayerId = PlayerController.getPlayerIdByFullName((String) homePlayerComboBoxes[i].getSelectedItem());
                    int homeGoals = Integer.parseInt(homeGoalFields[i].getText());

                    PlayerController.addPlayerMatchStats(homePlayerId, TeamController.getTeamIdByName(homeTeam), matchId, homeGoals, PlayerController.getRoleIdByPlayerId(homePlayerId));
                }

                // Itera sui giocatori ospiti
                for (int i = 0; i < 11; i++) {
                    int guestPlayerId = PlayerController.getPlayerIdByFullName((String) guestPlayerComboBoxes[i].getSelectedItem());
                    int guestGoals = Integer.parseInt(guestGoalFields[i].getText());
                    PlayerController.addPlayerMatchStats(guestPlayerId, TeamController.getTeamIdByName(guestTeam), matchId, guestGoals, PlayerController.getRoleIdByPlayerId(guestPlayerId));
                }

                // Chiudi il frame dopo l'inserimento dei dati nel database

                frame.dispose();
                List<Player> userData = PlayerController.getPlayers();
                new MainFrame(userData);
                new CheckFrame("Success");
            }
        });
    }
}