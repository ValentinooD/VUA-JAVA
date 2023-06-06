package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class UsersTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES
            = {"Id", "Username", "Role"};
    
    private List<User> users;

    public UsersTableModel(List<User> movies) {
        this.users = movies;
    }

    public void setUsers(List<User> movies) {
        this.users = movies;
        fireTableDataChanged();
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getUsername();
            case 2:
                return user.getRole().toString();
        }
        
        return "None";
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
}
