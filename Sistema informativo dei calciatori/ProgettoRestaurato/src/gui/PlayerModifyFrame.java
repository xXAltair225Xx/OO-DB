package gui;

import controller.PlayerController;
import dao.PlayerDAO;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PlayerModifyFrame {

    private static JFrame frame;
    private static JPanel panel;

    private static JLabel usernameLabel;
    private static JTextField usernameTextField;

    private static JButton nameButton;
    private static JButton surnameButton;
    private static JButton passwordButton;
    private static JButton footButton;
    private static JButton roleButton;
    private static JButton characteristicsButton;
    private static JButton backButton;
    private static JButton retireButton;
    private static JButton trophyButton;
    private static JButton careerButton;

    public PlayerModifyFrame() {

        PlayerDAO playerDAO = new PlayerDAO();
        PlayerController playerController = new PlayerController();

        panel = new JPanel(new GridLayout(4, 2));
        frame = new JFrame("Modify player data");


        usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(WelcomeFrame.username);
        usernameTextField.setEditable(false);

        nameButton = new JButton("Name");
        surnameButton = new JButton("Surname");
        passwordButton = new JButton("Password");
        backButton = new JButton("Go back");
        retireButton = new JButton("Retire");




        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);


        // Bottone per tornare indietro
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chiudi la finestra di modifica e torna alla schermata principale del giocatore
                frame.dispose();
                List<Player> userData = PlayerController.getPlayers();
                new MainFrame(userData);
                // Puoi aprire la finestra principale del giocatore se necessario
                // Esempio: new PlayerMainFrame(username);
            }
        });

        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerModifyAlterFrame("Name",WelcomeFrame.username);
            }
        });
        surnameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerModifyAlterFrame("Surname",WelcomeFrame.username);
            }
        });
        passwordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerModifyAlterFrame("Password",WelcomeFrame.username);
            }
        });
        retireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new CheckFrame("Are you sure?");

            }
        });
        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(nameButton);
        panel.add(surnameButton);
        panel.add(passwordButton);
        panel.add(backButton);

        if(PlayerController.getRetirementDate(WelcomeFrame.username) == null){
            panel.add(retireButton);
        }
        frame.add(panel);
        frame.setVisible(true);
    }


    // COSTRUTTORE PER VISTA ADMIN -------------------------------------------------------------------------------------

    public PlayerModifyFrame(String username) {

        panel = new JPanel(new GridLayout(6, 2));
        frame = new JFrame("Modify player data");
        usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(username);
        usernameTextField.setEditable(false);
        nameButton = new JButton("Name");
        surnameButton = new JButton("Surname");
        passwordButton = new JButton("Password");
        backButton = new JButton("Go back");
        footButton = new JButton("Foot");
        roleButton = new JButton("Role");
        characteristicsButton = new JButton("Characteristics");
        trophyButton = new JButton("Trophy");
        careerButton = new JButton("Career");

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Bottone per tornare indietro
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chiudi la finestra di modifica e torna alla schermata principale del giocatore
                frame.dispose();

                List<Player> userData = PlayerController.getPlayers();
                new MainFrame(userData);

                // Puoi aprire la finestra principale del giocatore se necessario
                // Esempio: new PlayerMainFrame(username);
            }
        });

        nameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerModifyAlterFrame("Name",username);
            }
        });
        surnameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerModifyAlterFrame("Surname",username);
            }
        });
        passwordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerModifyAlterFrame("Password",username);
            }
        });
        footButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerModifyAlterFrame("Foot",username);
            }
        });
        roleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerModifyAlterFrame("Role",username);
            }
        });

        characteristicsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerModifyAlterFrame("Characteristics",username);
            }
        });





        trophyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlayerAddTrophyFrame(username);
            }
        });

        careerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCareerFrame(username);
            }

        });

        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(nameButton);
        panel.add(surnameButton);
        panel.add(passwordButton);
        panel.add(footButton);
        panel.add(roleButton);
        panel.add(characteristicsButton);

        if(controller.PlayerController.getRetirementDate(username)== null){
            panel.add(trophyButton);
        }
        panel.add(careerButton);
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
