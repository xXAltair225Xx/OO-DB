package gui;

import controller.PlayerController;
import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CheckFrame {

    private static JFrame frame;
    private static JPanel panel;
    private static JButton button;
    private static JPasswordField passwordCheck;
    private static JLabel label;
    private static JTextField usernameCheck;


    public CheckFrame(String labelValue) {
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel(labelValue);
        button = new JButton("Ok");
        usernameCheck = new JTextField();
        passwordCheck = new JPasswordField();


        frame.setSize(250, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        panel.setLayout(null);

        label.setBounds(85, 25, 200, 20);
        button.setBounds(75, 60, 80, 20);


        if (labelValue.equals("Are you sure?")) {
            frame.setSize(300,200);
            label.setBounds(95, 20, 200, 20);
            usernameCheck.setBounds(100, 45, 60, 20);
            passwordCheck.setBounds(100, 70, 60, 20);
            button.setBounds(95, 120, 80, 20);
            button.setText("Confirm");
            panel.add(usernameCheck);
            panel.add(passwordCheck);


            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    HashMap<String, String> playerPasswordMap = (HashMap<String, String>) PlayerController.loadPlayerCredentials();
                    String username = usernameCheck.getText();
                    String password = new String(passwordCheck.getPassword());
                    String storedPlayerHash = playerPasswordMap.get(username);

                    if (storedPlayerHash != null && BCrypt.checkpw(password, storedPlayerHash)) {
                        PlayerController.playerRetirement(username);
                    }
                }
            });


        }


        frame.add(panel);
        panel.add(label);
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);

    }
}
