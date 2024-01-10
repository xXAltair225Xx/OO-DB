import org.jdesktop.swingx.JXDatePicker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PlayerAddFrame {
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameTextField;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField piedeTextField;
    private JXDatePicker datePicker;
    private JPasswordField passwordTextField;

    public PlayerAddFrame() {
        frame = new JFrame("Add player");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(7, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        passwordTextField = new JPasswordField();
        JLabel nomeLabel = new JLabel("Nome:");
        nomeTextField = new JTextField();
        JLabel cognomeLabel = new JLabel("Cognome:");
        cognomeTextField = new JTextField();
        JLabel dataNascitaLabel = new JLabel("Data di Nascita:");
        datePicker = new JXDatePicker();
        JLabel piedeLabel = new JLabel("Piede");
        piedeTextField = new JTextField();

        datePicker.setDate(new Date());
        datePicker.setFormats(new SimpleDateFormat("dd/MM/yyyy"));

        JButton addPlayerButton = new JButton("Aggiungi");
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ottieni i dati inseriti
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                String nome = nomeTextField.getText();
                String cognome = cognomeTextField.getText();
                String piede = piedeTextField.getText();

                // Converti la data selezionata
                LocalDate localDate = datePicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Date dataNascita = java.sql.Date.valueOf(localDate);

                // Esegui l'aggiunta nel database
                DBManager.addNewPlayer(username, password, nome, cognome, (java.sql.Date) dataNascita, piede);

                // Chiudi il frame di aggiunta
                usernameTextField.setText("");
                passwordTextField.setText("");
                nomeTextField.setText("");
                cognomeTextField.setText("");
                piedeTextField.setText("");

                new FrameCheck();
            }
        });

        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Chiudi il frame di aggiunta e torna alla schermata iniziale
                frame.dispose();
                Object[][] userData = DBManager.getUserData();
                new MainFrame(userData,"Bella", "Admin");
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(nomeLabel);
        panel.add(nomeTextField);
        panel.add(cognomeLabel);
        panel.add(cognomeTextField);
        panel.add(dataNascitaLabel);
        panel.add(datePicker);
        panel.add(piedeLabel);
        panel.add(piedeTextField);
        panel.add(addPlayerButton);
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}