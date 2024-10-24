package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import controller.PlayerController;
import controller.UtilController;
import model.Player;
import org.mindrot.jbcrypt.BCrypt;

public class LoginFrame implements ActionListener {
    private UtilController utilController;
    private JFrame frame;
    private JPanel panel;
    private JLabel userLabel;
    private JTextField userText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JLabel successLabel;
    private JButton loginBackButton;

    public LoginFrame() {

        utilController = new UtilController();
        frame = new JFrame("Login");
        panel = new JPanel();
        userLabel = new JLabel("User");
        userText = new JTextField();
        passwordLabel = new JLabel("Password");
        passwordText = new JPasswordField();
        loginButton = new JButton("Login");
        loginBackButton = new JButton("Go back");
        successLabel = new JLabel("");

        frame.setSize(350, 200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        panel.setLayout(null);


        userLabel.setBounds(10, 20, 80, 25);
        userText.setBounds(100, 20, 165, 25);
        passwordLabel.setBounds(10, 50, 80, 25);
        passwordText.setBounds(100, 50, 165, 25);
        loginButton.setBounds(180, 90, 100, 25);
        loginButton.addActionListener(this);
        loginBackButton.setBounds(40, 90, 100, 25);
        loginBackButton.addActionListener(this);
        successLabel.setBounds(10, 120, 165, 25);
        panel.add(successLabel);

        frame.add(panel);
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);
        panel.add(loginBackButton);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = new String(passwordText.getPassword());

        HashMap<String, String> adminPasswordMap = UtilController.loadCredentials(user,password,"admin");
        HashMap<String, String> playerPasswordMap = UtilController.loadCredentials(user,password,"player");
        HashMap<String, String> coachPasswordMap = UtilController.loadCredentials(user,password,"coach");
        HashMap<String, String> managerPasswordMap = UtilController.loadCredentials(user,password,"manager");

        String storedPlayerHash = playerPasswordMap.get(user);
        String storedAdminHash = adminPasswordMap.get(user);
        String storedCoachHash = coachPasswordMap.get(user);
        String storedManagerHash = managerPasswordMap.get(user);

        if (e.getSource() == loginButton && storedAdminHash != null && BCrypt.checkpw(password, storedAdminHash)) {
            frame.dispose();
            List<Player> userData = PlayerController.getPlayers();
            WelcomeFrame.role = "Admin";
            WelcomeFrame.username = user;
            new MainFrame(userData);

        } else if (e.getSource() == loginButton && storedPlayerHash != null && BCrypt.checkpw(password, storedPlayerHash)) {
            frame.dispose();
            List<Player> userData = PlayerController.getPlayers();
            WelcomeFrame.role = "Player";
            WelcomeFrame.username = user;
            new MainFrame(userData);

        }
        else if (e.getSource() == loginButton && storedCoachHash != null && BCrypt.checkpw(password, storedCoachHash)) {
            frame.dispose();
            List<Player> userData = PlayerController.getPlayers();
            WelcomeFrame.role = "Coach";
            WelcomeFrame.username = user;
            new MainFrame(userData);

        }
        else if (e.getSource() == loginButton && storedManagerHash != null && BCrypt.checkpw(password, storedManagerHash)) {
            frame.dispose();
            List<Player> userData = PlayerController.getPlayers();
            WelcomeFrame.role = "Manager";
            WelcomeFrame.username = user;
            new MainFrame(userData);

        }

        else if (e.getSource() == loginButton) {
            successLabel.setText("Login failed");

        } else if (e.getSource()== loginBackButton) {
            frame.dispose();
            new WelcomeFrame();
        }
    }
}