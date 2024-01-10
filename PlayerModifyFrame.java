import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerModifyFrame {
    private JFrame frame;
    private JPanel panel;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JPasswordField passwordField;

    // Aggiungi altri campi necessari per la modifica dei dati

    public PlayerModifyFrame(String username) {
        frame = new JFrame("Modify player data");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(5, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameTextField = new JTextField(username);
        usernameTextField.setEditable(false);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeTextField = new JTextField();
        JLabel cognomeLabel = new JLabel("Cognome:");
        cognomeTextField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();


        // Bottone per modificare i propri dati

            JButton modifyButton = new JButton("Modifica Dati");
            modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ottieni i dati inseriti
                String nuovoNome = nomeTextField.getText();
                String nuovoCognome = cognomeTextField.getText();
                String nuovaPassword = passwordField.getText();

                // Esegui la modifica nel database
                DBManager.modifyPlayerData(username, nuovoNome, nuovoCognome,nuovaPassword);

                // Chiudi la finestra di modifica

                nomeTextField.setText("");
                cognomeTextField.setText("");
                passwordField.setText("");

                new FrameCheck();

            }
        });

        // Bottone per tornare indietro

            JButton backButton = new JButton("Go back");
            backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chiudi la finestra di modifica e torna alla schermata principale del giocatore
                frame.dispose();
                Object[][] userData = DBManager.getUserData();
                new MainFrame(userData,username,"Player");
                // Puoi aprire la finestra principale del giocatore se necessario
                // Esempio: new PlayerMainFrame(username);
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(nomeLabel);
        panel.add(nomeTextField);
        panel.add(cognomeLabel);
        panel.add(cognomeTextField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(modifyButton);
        panel.add(backButton);


        frame.add(panel);
        frame.setVisible(true);
    }
}
