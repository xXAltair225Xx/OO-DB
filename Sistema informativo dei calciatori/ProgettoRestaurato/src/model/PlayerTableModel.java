package model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PlayerTableModel extends AbstractTableModel {
    private List<Player> players;
    private String[] columnNames;

    public PlayerTableModel(List<Player> players, String[] columnNames) {

        this.players = players;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return players.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Player player = players.get(rowIndex);

        switch (columnIndex) {
            case 0: return player.getName();
            case 1: return player.getSurname();
            case 2: return player.calcolaEta();
            case 3: return player.getRetireDate();
            case 4: return player.getFoot();
            case 5: return player.getPlayer_role();
            case 6: return player.getTotalScoredGoal();
            case 7: return player.getTotalConcededGoal();
            case 8: return player.getCurrentTeam();

            // Aggiungi altri casi per le colonne rimanenti...
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

}