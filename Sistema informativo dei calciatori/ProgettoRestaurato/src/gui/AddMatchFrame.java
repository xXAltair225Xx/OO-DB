package gui;

import controller.CareerController;
import controller.MatchController;
import controller.PlayerController;
import controller.TeamController;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class AddMatchFrame {
    private JFrame frame;
    private JPanel panel;
    private JLabel homeTeamLabel;
    private JLabel guestTeamLabel;
    private JLabel dateLabel;
    private JSpinner dateSpinner;
    private JComboBox<String> homeTeamComboBox;
    private JComboBox<String> guestTeamComboBox;
    private JButton nextButton, backButton;

    public AddMatchFrame() {
        frame = new JFrame("Add New Match");
        panel = new JPanel(new GridLayout(4, 2));

        homeTeamLabel = new JLabel("Home Team:");
        guestTeamLabel = new JLabel("Guest Team:");
        dateLabel = new JLabel("Date:");
        backButton = new JButton("Back");


        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date());

        String[] teamName = TeamController.getTeamName();
        homeTeamComboBox = new JComboBox<>(teamName);
        guestTeamComboBox = new JComboBox<>(teamName);

        nextButton = new JButton("Next");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        panel.add(homeTeamLabel);
        panel.add(homeTeamComboBox);
        panel.add(guestTeamLabel);
        panel.add(guestTeamComboBox);
        panel.add(dateLabel);
        panel.add(dateSpinner);
        panel.add(nextButton);
        panel.add(backButton);
        frame.add(panel);
        frame.setVisible(true);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String homeTeam = (String) homeTeamComboBox.getSelectedItem();
                String guestTeam = (String) guestTeamComboBox.getSelectedItem();
                java.util.Date matchDate = (java.util.Date) dateSpinner.getValue();
                java.sql.Date sqlDate = new java.sql.Date(matchDate.getTime());

                // Passa al frame successivo per selezionare la formazione
                frame.dispose();
                int matchID = MatchController.addNewMatch(TeamController.getTeamIdByName(homeTeam), TeamController.getTeamIdByName(guestTeam), sqlDate);
                new SelectFormationFrame(homeTeam, guestTeam, matchID);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                List<Player> userData = PlayerController.getPlayers();
                new MainFrame(userData);
            }
        });
    }
}