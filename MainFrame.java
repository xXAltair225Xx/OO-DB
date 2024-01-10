import javax.swing.*;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MainFrame {
    public static JFrame mainFrame;
    public static JPanel mainPanel;
    public static JXTable dataTable;


    private static JButton profileButton;

    public MainFrame(Object[][] userData, String user, String role) {

        mainFrame = new JFrame("Player list");
        mainPanel = new JPanel();

        mainFrame.setSize(800, 600);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(mainPanel);

        mainPanel.setLayout(null);

        String[] columnNames = {"Nome", "Cognome", "Data di Nascita", "Piede", "Trofei", "Ritiro", "Ruolo"};

        dataTable = new JXTable(userData, columnNames);
        //Servono ad abilitare la singola selezione delle righe
        ListSelectionModel selectionModel = dataTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Serve ad aggiungere un'ascoltaore per gestire gli eventi di selezione
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                //Serve ad abilitare o disattivare il pulsante della visualizzazione della selezione della riga
                profileButton.setEnabled(!selectionModel.isSelectionEmpty());
            }
        });

        //Aggiunge sfondi alternati alle righe(solo il primo)
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(45, 10, 10, 200);  // Imposta le dimensioni e la posizione dello JScrollPane manualmente
        mainPanel.add(scrollPane);
        dataTable.setHighlighters(HighlighterFactory.createAlternateStriping());

        //Questo è un mouse listener per gestire i click sulla tabbella
        dataTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {  // Rileva un singolo clic
                    int selectedRow = dataTable.rowAtPoint(e.getPoint());
                    if (selectedRow != -1) {
                        // Ottieni i dati dell'utente selezionato
                        Object[] rowData = new Object[columnNames.length];
                        for (int i = 0; i < columnNames.length; i++) {
                            rowData[i] = dataTable.getValueAt(selectedRow, i);
                        }

                        // Apri una finestra di dettaglio per l'utente selezionato
                        showUserDetails(rowData);
                    }
                }
            }
        });



        dataTable.getTableHeader().setBounds(45, 30,700,20);
        dataTable.setBounds(45,50,700,200);


        mainPanel.add(dataTable.getTableHeader());
        mainPanel.add(dataTable);

        profileButton = new JButton("Visualizza Dettagli Utente");
        profileButton.setEnabled(false);  // Inizialmente disabilitato finché non viene selezionata una riga
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Ottieni i dati dell'utente selezionato
                    Object[] rowData = new Object[columnNames.length];
                    for (int i = 0; i < columnNames.length; i++) {
                        rowData[i] = dataTable.getValueAt(selectedRow, i);
                    }

                    // Apri una finestra di dettaglio per l'utente selezionato
                    showUserDetails(rowData);
                }
            }
        });

        mainPanel.add(profileButton);

    //Schermata del DB vista dal Guest
        if(role == "Guest") {

                    //Creazione bottone per tornare indietro

                    JButton backButton = new JButton("Go back");
                    backButton.setBounds(10, 500, 80, 25);
                    backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Chiudi la finestra di login e apri la schermata iniziale
                            mainFrame.dispose();
                            new WelcomeFrame();
                        }
                    });
            mainPanel.add(backButton);

        }

    //Schermata del DB vista dal Giocatore
        else if (role == "Player") {

                    //Creazione bottone per tornare indietro

                    JButton backButton = new JButton("Log out");
                    backButton.setBounds(10, 500, 80, 25);
                    backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Chiudi la finestra di login e apri la schermata iniziale
                            mainFrame.dispose();
                            new LoginFrame();
                        }
                    });

                    //Creazione bottone per modificare i dati del giocatore come Giocatore

                    JButton playerModifyButton = new JButton("Edit Profile");
                    playerModifyButton.setBounds(120, 500, 100, 25);
                    playerModifyButton.addActionListener(new ActionListener() {
                        @Override

                        public void actionPerformed(ActionEvent e) {
                            // Chiudi la finestra di login e apri la schermata iniziale
                            mainFrame.dispose();
                            new PlayerModifyFrame(user);
                        }
                    });

            mainPanel.add(backButton);
            mainPanel.add(playerModifyButton);

        }
        //Schermata del DB vista dall'amministratore

        else if (role == "Admin") {

                     //Creazione bottone per tornare indietro

                    JButton backButton = new JButton("Log out");
                    backButton.setBounds(10, 500, 80, 25);
                    backButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Chiudi la finestra di login e apri la schermata iniziale
                            mainFrame.dispose();
                            new LoginFrame();
                        }
                    });

                     //Creazione bottone per aggiungere un nuovo giocatore come Amministratore

                    JButton addPlayerButton = new JButton("Add Player");
                    addPlayerButton.setBounds(200, 500, 100, 25);
                    addPlayerButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Chiudi la finestra di login e apri la schermata iniziale
                            mainFrame.dispose();
                            new PlayerAddFrame();
                        }
                    });

            mainPanel.add(addPlayerButton);
            mainPanel.add(backButton);

        }

        mainFrame.setVisible(true);
    }
    private void showUserDetails(Object[] rowData) {
       mainFrame.dispose();
       new WelcomeFrame();
    }
}
