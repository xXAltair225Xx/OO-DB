package gui;

import controller.CareerController;
import controller.TeamController;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;
public class AddCareerFrame extends JFrame {
    private JLabel usernameLabel, teamLabel, startDateLabel, endDateLabel;
    private JTextField usernameField;
    private JComboBox<String> teamComboBox;
    private JXDatePicker startDatePicker, endDatePicker;
    private JButton addButton;

    public AddCareerFrame(String username) {
        setTitle("Add Career");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra il frame nella finestra

        // Inizializza i componenti
        usernameLabel = new JLabel("Username:");
        teamLabel = new JLabel("Team:");
        startDateLabel = new JLabel("Start Date:");
        endDateLabel = new JLabel("End Date:");
        setResizable(false);

        usernameField = new JTextField(username);
        usernameField.setEditable(false);


        teamComboBox = new JComboBox<>(TeamController.getTeamName());

        startDatePicker = new JXDatePicker();
        endDatePicker = new JXDatePicker();

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String team = (String) teamComboBox.getSelectedItem();

                LocalDate localStartDate = null;
                java.sql.Date startDate = null;
                if (startDatePicker.getDate() != null) {
                    localStartDate = startDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    startDate = java.sql.Date.valueOf(localStartDate);
                }

                LocalDate localEndDate = null;
                java.sql.Date endDate = null;
                if (endDatePicker.getDate() != null) {
                    localEndDate = endDatePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    endDate = java.sql.Date.valueOf(localEndDate);
                }

                CareerController.addCareer(username, team, startDate, endDate);
                dispose(); // Chiude il frame dopo l'aggiunta
            }
        });


        // Layout del frame
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(teamLabel);
        panel.add(teamComboBox);
        panel.add(startDateLabel);
        panel.add(startDatePicker);
        panel.add(endDateLabel);
        panel.add(endDatePicker);
        panel.add(addButton);


        add(panel);
        setVisible(true);
    }
}

