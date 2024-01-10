import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;

public class LoginFrame implements ActionListener {
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
        frame = new JFrame("Login");
        panel = new JPanel();


        frame.setSize(350, 200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(panel);

        panel.setLayout(null);

        userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(180, 90, 100, 25);
        loginButton.addActionListener(this::actionPerformed);
        panel.add(loginButton);

        loginBackButton = new JButton("Main menu");
        loginBackButton.setBounds(40, 90, 100, 25);
        loginBackButton.addActionListener(this::actionPerformed);
        panel.add(loginBackButton);

        successLabel = new JLabel("");
        successLabel.setBounds(10, 120, 165, 25);
        panel.add(successLabel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = new String(passwordText.getPassword());

        HashMap<String, String> adminPasswordMap = DBManager.getAdminUserPasswordMap();
        HashMap<String, String> playerPasswordMap = DBManager.getPlayerUserPasswordMap();

        if (adminPasswordMap.containsKey(user) && password.equals(adminPasswordMap.get(user)) && e.getSource() == loginButton) {
            frame.dispose();
            Object[][] userData = DBManager.getUserData();
            new MainFrame(userData, user, "Admin");

        } else if (playerPasswordMap.containsKey(user) && password.equals(playerPasswordMap.get(user)) && e.getSource() == loginButton) {
            frame.dispose();
            Object[][] userData = DBManager.getUserData();
            new MainFrame(userData, user, "Player");
        } else if (e.getSource() == loginButton) {
            successLabel.setText("Login failed");
        } else if (e.getSource() == loginBackButton) {
            frame.dispose();
            new WelcomeFrame();


        }
    }
}