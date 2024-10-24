package gui;

import controller.CareerController;
import controller.PlayerController;
import controller.TeamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class PlayerAddTrophyFrame {
    private static JFrame frame;
    private static JPanel panel;
    private static JPanel buttonPanel;
    private static JButton button, backButton;
    private static JLabel trophyLabel;
    private static JComboBox trophyComboBox;
    private static JComboBox teamComboBox;
    private static JLabel teamLabel;

    private static JLabel dateLabel;
    private JSpinner dateSpinner;


    public PlayerAddTrophyFrame(String username) {


        frame = new JFrame();
        panel = new JPanel();
        buttonPanel = new JPanel();
        button = new JButton("Add Trophy");
        trophyLabel = new JLabel("Trophy");
        trophyComboBox = new JComboBox<>(CareerController.getTrophyName());
        dateLabel = new JLabel("Date");
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date());

        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(2,1));
        panel.setLayout(new GridLayout(3,2));
        buttonPanel.setLayout(new BorderLayout());



        panel.add(trophyLabel);
        panel.add(trophyComboBox);
        panel.add(dateLabel);
        panel.add(dateSpinner);
        buttonPanel.add(button, BorderLayout.CENTER);
        frame.add(panel);
        frame.add(buttonPanel);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date trophyDate = (java.util.Date) dateSpinner.getValue();
                java.sql.Date sqlDate = new java.sql.Date(trophyDate.getTime());
                PlayerController.addPlayerTrophy(PlayerController.getPlayerIdByUsername(username),trophyComboBox.getSelectedIndex()+1, sqlDate );
                new CheckFrame("Success");
            }
        });
    }


    public PlayerAddTrophyFrame() {


        frame = new JFrame();
        panel = new JPanel();
        buttonPanel = new JPanel();
        button = new JButton("Add Trophy");
        backButton = new JButton("Back");
        teamLabel = new JLabel("Team");
        teamComboBox = new JComboBox<>(TeamController.getTeamName());
        trophyLabel = new JLabel("Trophy");
        trophyComboBox = new JComboBox<>(TeamController.getTeamTrophyName());
        dateLabel = new JLabel("Year");
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date());

        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new GridLayout(1,1));
        panel.setLayout(new GridLayout(4,2));
        buttonPanel.setLayout(new BorderLayout());


        panel.add(teamLabel);
        panel.add(teamComboBox);
        panel.add(trophyLabel);
        panel.add(trophyComboBox);
        panel.add(dateLabel);
        panel.add(dateSpinner);
        panel.add(backButton);
        panel.add(button);
        frame.add(panel);
        frame.setVisible(true);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date trophyDate = (java.util.Date) dateSpinner.getValue();
                java.sql.Date sqlDate = new java.sql.Date(trophyDate.getTime());
                TeamController.addTeamTrophy(TeamController.getTeamIdByName((String) teamComboBox.getSelectedItem()),trophyComboBox.getSelectedIndex()+1,  sqlDate);

                new CheckFrame("Success");
                frame.dispose();

            }
        });
    }


}
