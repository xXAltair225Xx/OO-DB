package gui;

import controller.PlayerController;
import dao.PlayerDAO;
import model.Player;
import org.jdesktop.swingx.JXDatePicker;
import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class PlayerAddFrame {
    private JFrame frame;
    private JPanel panel;

    private static JButton addPlayerButton;
    private static JLabel usernameLabel;
    private static JLabel passwordLabel;
    private static JLabel nameLabel;
    private static JLabel surnameLabel;
    private static JLabel birthDateLabel;
    private static JLabel footLabel;
    private static JLabel roleLabel;
    private JTextField usernameTextField;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JXDatePicker datePicker;
    private JPasswordField passwordTextField;
    private JComboBox<String> footComboBox;
    private static JList<String> roleList;
    private static JScrollPane roleScrollPane;



    private static String[] foot = new String[]{"Right", "Left", "Ambidexter"};
    private static String[] role = new String[]{"GoalKeeper", "Defender", "MidFielder", "Forward"};
    private static String[] trophies = new String[]{"Coppa del mondo"};


    public PlayerAddFrame() {
        PlayerDAO playerDAO = new PlayerDAO();
        PlayerController playerController = new PlayerController();

        frame = new JFrame("Add player");
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        panel = new JPanel(new GridLayout(10, 2));

        addPlayerButton = new JButton("Add");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        nameLabel = new JLabel("Name:");
        surnameLabel = new JLabel("Surname:");
        birthDateLabel = new JLabel("Birth date:");
        footLabel = new JLabel("Foot:");
        roleLabel = new JLabel("Roles:");

        usernameTextField = new JTextField();
        nameTextField = new JTextField();
        surnameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        datePicker = new JXDatePicker();
        footComboBox = new JComboBox<>(foot);
        roleList = new JList<>(role);
        roleScrollPane = new JScrollPane(roleList);

        datePicker.setDate(new Date());
        datePicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));


        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Ottieni i dati inseriti

                String playerUsername = usernameTextField.getText();
                String nome = nameTextField.getText();
                String cognome = surnameTextField.getText();
                String piede = (String) footComboBox.getSelectedItem();
                LocalDate localDate = datePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                java.sql.Date dataNascita = java.sql.Date.valueOf(localDate);
                String playerPassword = BCrypt.hashpw(passwordTextField.getText(), BCrypt.gensalt());

                int[] selectedIndices = roleList.getSelectedIndices();
                List<Integer> selectedRoles = new ArrayList<>();
                for (int selectedIndex : selectedIndices) {
                    selectedRoles.add(selectedIndex + 1);
                }


                // Esegui l'aggiunta nel database
                PlayerController.addNewPlayer(playerUsername, playerPassword, nome, cognome, dataNascita, piede, selectedRoles);


                usernameTextField.setText("");
                passwordTextField.setText("");
                nameTextField.setText("");
                surnameTextField.setText("");

            }
        });

        JButton backButton = new JButton("Go back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Chiudi il frame di aggiunta e torna alla schermata iniziale
                frame.dispose();
                List<Player> userData = PlayerController.getPlayers();
                new MainFrame(userData);
            }
        });


        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(surnameLabel);
        panel.add(surnameTextField);
        panel.add(birthDateLabel);
        panel.add(datePicker);
        panel.add(footLabel);
        panel.add(footComboBox);
        panel.add(roleLabel);
        panel.add(roleScrollPane);
        panel.add(new JLabel(" "));
        panel.add(new JLabel(" "));
        panel.add(addPlayerButton);
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}



