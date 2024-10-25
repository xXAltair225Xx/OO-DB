package gui;

import controller.PlayerController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class PlayerModifyAlterFrame {

    private JFrame frame;
    private JPanel panel;
    private JButton backButton;
    private JButton modifyButton;
    private JLabel label;
    private JTextField field;
    private JPasswordField passwordField;
    private JComboBox<String> footComboBox;
    private JList<String> roleList;
    private JList<String> characteristicsList;
    private String campo;

    public PlayerModifyAlterFrame(String button, String username) {

        panel = new JPanel(new GridLayout(2, 2));
        frame = new JFrame("Modify data");

        if (button.equals("Name")) {
            label = new JLabel("Name");
            field = new JTextField();
            panel.add(label);
            panel.add(field);
            campo = "Name";
        } else if (button.equals("Surname")) {
            label = new JLabel("Surname");
            field = new JTextField();
            panel.add(label);
            panel.add(field);
            campo = "Surname";
        } else if (button.equals("Password")) {
            label = new JLabel("Password");
            passwordField = new JPasswordField();
            panel.add(label);
            panel.add(passwordField);
            campo = "Password";
        } else if (button.equals("Foot")) {
            label = new JLabel("Foot");
            String[] foot = new String[]{"Right", "Left", "Ambidexter"};
            footComboBox = new JComboBox<>(foot);
            panel.add(label);
            panel.add(footComboBox);
            campo = "Foot";
        } else if (button.equals("Role")) {
            label = new JLabel("Roles");
            roleList = new JList<>(PlayerController.getRolesName());
            roleList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane roleScrollPane = new JScrollPane(roleList);
            panel.add(label);
            panel.add(roleScrollPane);
            campo = "Role";
        } else if (button.equals("Characteristics")) {
            label = new JLabel("Characteristics");
            characteristicsList = new JList<>(PlayerController.getCharacteristicName());
            characteristicsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane characteristicsScrollPane = new JScrollPane(characteristicsList);
            panel.add(label);
            panel.add(characteristicsScrollPane);
            campo = "Characteristics";
        }

        backButton = new JButton("Back");
        backButton.addActionListener(e -> frame.dispose());

        modifyButton = new JButton("Modify");
        modifyButton.addActionListener(e -> {
            frame.dispose();
            String nuovo = null;
            List<Integer> selectedRoles = new ArrayList<>();
            int playerId = PlayerController.getPlayerIdByUsername(username);

            if (field != null) {
                nuovo = field.getText();
            } else if (passwordField != null) {
                nuovo = new String(passwordField.getPassword());
            } else if (footComboBox != null) {
                nuovo = (String) footComboBox.getSelectedItem();
            } else if (roleList != null && roleList.getSelectedIndices().length > 0) {
                for (int selectedIndex : roleList.getSelectedIndices()) {
                    selectedRoles.add(selectedIndex + 1);
                }
            } else if (characteristicsList != null && characteristicsList.getSelectedIndices().length > 0) {
                PlayerController.deletePlayerRoleOrCharacteristic(playerId, "Characteristics");
                for (int index : characteristicsList.getSelectedIndices()) {
                    PlayerController.modifyPlayerCharacteristics(playerId, index + 1);
                }
            }

            if (nuovo != null) {
                PlayerController.modifyPlayerData(username, campo, nuovo);
            } else if (!selectedRoles.isEmpty()) {
                PlayerController.deletePlayerRoleOrCharacteristic(playerId, "Role");
                for (int index : selectedRoles) {
                    PlayerController.modifyPlayerRole(playerId,  selectedRoles);
                }
            }
        });

        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(panel);
        panel.add(backButton);
        panel.add(modifyButton);

        frame.setVisible(true);
    }
}
