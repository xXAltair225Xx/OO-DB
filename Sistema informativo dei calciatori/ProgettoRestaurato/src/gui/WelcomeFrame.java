package gui;

import controller.PlayerController;
import controller.UtilController;
import model.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class WelcomeFrame implements ActionListener {
    private UtilController utilController;
    private JFrame frameWelcome;
    private JPanel panelWelcome;
    private JLabel labelWelcome;
    private JButton buttonLoginWelcome;
    private JButton buttonGuestWelcome;
    public static String role;
    public static String username = "NULL";
    public WelcomeFrame() {

        role = "NULL"; //resets role to the base value in case of logout
        utilController = new UtilController();

        frameWelcome = new JFrame("Football player manager");
        panelWelcome = new JPanel();
        labelWelcome = new JLabel();
        buttonGuestWelcome = new JButton("Guest");
        buttonLoginWelcome = new JButton("Login");

        frameWelcome.setSize(350, 200);
        frameWelcome.setResizable(false);
        frameWelcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameWelcome.setLocationRelativeTo(null);
        frameWelcome.add(panelWelcome);


        labelWelcome.setText("Welcome to Football player manager");
        labelWelcome.setBounds(60, 22, 300, 25);
        panelWelcome.setLayout(null);
        panelWelcome.add(labelWelcome);

        panelWelcome.setBackground(Color.LIGHT_GRAY);
        buttonGuestWelcome.setBounds(48, 70, 100, 25);
        buttonLoginWelcome.setBounds(180, 70, 100, 25);
        panelWelcome.add(buttonGuestWelcome);
        panelWelcome.add(buttonLoginWelcome);
        buttonLoginWelcome.addActionListener(this::actionPerformed);
        buttonGuestWelcome.addActionListener(this::actionPerformed);


        frameWelcome.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonGuestWelcome) {
            frameWelcome.dispose();
            List<Player> userData = PlayerController.getPlayers();

            WelcomeFrame.role = "Guest";
            WelcomeFrame.username = "Guest";
            new MainFrame(userData);

        } else if (e.getSource() == buttonLoginWelcome) {
            frameWelcome.dispose();
            new LoginFrame();
        }


    }


}
