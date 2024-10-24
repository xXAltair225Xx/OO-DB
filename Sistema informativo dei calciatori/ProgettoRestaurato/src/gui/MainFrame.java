package gui;

import model.CareerInfo;
import model.Player;
import model.PlayerTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import javax.swing.RowFilter;
import java.awt.event.*;

public class MainFrame {

    private String role;
    private String username;

    private int selectedRow = -1;

    public MainFrame(List<Player> players) {

        JFrame mainFrame = new JFrame("main.Main Frame");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

        // Creazione delle colonne per la tabella
        String[] columnNames = {"Name", "Surname", "Age", "Retire Date", "Foot", "Roles", "Goal Scored", "Goal Conceded", "Team"};

        // Creazione del modello della tabella
        PlayerTableModel model = new PlayerTableModel(players, columnNames);

        // Creazione della tabella
        JTable dataTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        // Imposta il layout del frame principale
        mainFrame.setLayout(new BorderLayout());

        // Aggiungi le etichette per il ruolo e l'utente nella parte superiore della finestra
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(new JLabel("Role: " + WelcomeFrame.role));
        labelPanel.add(new JLabel("User: " + WelcomeFrame.username));
        mainFrame.add(labelPanel, BorderLayout.NORTH);

        // Aggiungi la tabella al centro del pannello principale
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        // Aggiungi i bottoni in base al ruolo dell'utente
        JPanel buttonPanel = new JPanel();
        if (WelcomeFrame.role.equals("Admin")) {
            JButton button1 = new JButton("Log out");
            JButton button5 = new JButton("Add match");
            JButton button6 = new JButton("Add Team Trophies");

            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.dispose();
                    new LoginFrame();
                }
            });
            button5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.dispose();
                    new AddMatchFrame();
                }
            });

            button6.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new PlayerAddTrophyFrame();
                }
            });


            buttonPanel.add(button1);
            buttonPanel.add(button5);
            buttonPanel.add(button6);


            JButton button2 = new JButton("Add player");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.dispose();
                    new PlayerAddFrame();
                }
            });
            buttonPanel.add(button2);
        } else if (WelcomeFrame.role.equals("Player") || WelcomeFrame.role.equals("Coach") || WelcomeFrame.role.equals("Manager")) {
            JButton button3 = new JButton("Log out");
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.dispose();
                    new LoginFrame();
                }
            });
            buttonPanel.add(button3);

            JButton button4 = new JButton("Edit profile");
            button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.dispose();
                    new PlayerModifyFrame();
                }
            });
            buttonPanel.add(button4);
        } else { // Guest
            JButton button5 = new JButton("Go back");
            button5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainFrame.dispose();
                    new WelcomeFrame();
                }
            });
            buttonPanel.add(button5);
        }
        mainFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Aggiungi il filtro per le colonne
        addColumnFilters(dataTable);

        // Aggiungi il ListSelectionListener per la tabella per gestire il clic su una riga
        ListSelectionModel selectionModel = dataTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int viewRow = dataTable.getSelectedRow();
                    if (viewRow != -1) {
                        int modelRow = dataTable.convertRowIndexToModel(viewRow);
                        selectedRow = modelRow;
                        Player selectedPlayer = players.get(modelRow);
                        List<CareerInfo> careerInfoList = selectedPlayer.getCareerInfoList();
                        mainFrame.dispose();
                        new PlayerProfileFrame(selectedPlayer, careerInfoList);
                    }
                }
            }
        });

        mainFrame.setVisible(true);
    }

    private void addColumnFilters(JTable table) {
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Utilizziamo FlowLayout per posizionare i componenti da sinistra a destra
        table.getTableHeader().add(filterPanel, BorderLayout.CENTER);

        // Aggiungi campi di filtro per le colonne
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i);
            JTextField filterField = new JTextField();
            filterField.setPreferredSize(new Dimension(100, 25));
            filterPanel.add(new JLabel(columnName + ": "));
            filterPanel.add(filterField);
            final int index = i;
            filterField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    applyFilter();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    applyFilter();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    applyFilter();
                }

                private void applyFilter() {
                    String text = filterField.getText();
                    if (text.trim().length() == 0) {
                        rowSorter.setRowFilter(null);
                    } else {
                        // Esegue il filtro per la colonna corrispondente
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, index));
                    }
                }
            });
        }
    }

}
