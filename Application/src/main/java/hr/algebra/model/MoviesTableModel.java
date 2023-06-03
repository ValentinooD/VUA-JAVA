package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MoviesTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES
            = {"Id", "Title", "Director", "Publish date", "Showing date"};
    
    private List<Movie> movies;

    public MoviesTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movie movie = movies.get(rowIndex);
        
        switch (columnIndex) {
            case 0 -> {
                return movie.getId();
            }
            case 1 -> {
                return movie.getTitle();
            }
            case 2 -> {
                return movie.getDirector();
            }
            case 3 -> {
                return movie.getPubishDate().toString();
            }
            case 4 -> {
                return movie.getShowingDate().toString();
            }
        }
        
        return "None";
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }
}
