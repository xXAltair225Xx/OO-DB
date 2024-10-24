package gui;

import model.Match;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MatchesPlayedFrame extends JFrame{
    private JTable matchesTable;

    public MatchesPlayedFrame(List<Match> matches) {
        setTitle("Matches Played");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        String[] columnNames = {"Home Team", "Guest Team", "Home Goals", "Guest Goals", "Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        matchesTable = new JTable(model);

        for (Match match : matches) {
            Object[] rowData = {match.getHomeTeam(), match.getGuestTeam(), match.getHomeGoals(), match.getGuestGoals(), match.getDate()};
            model.addRow(rowData);
        }

        JScrollPane scrollPane = new JScrollPane(matchesTable);

        add(scrollPane);
        setVisible(true);
    }


}
