package hr.algebra.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MoviesTableModel extends AbstractTableModel {
    private static final String[] COLUMN_NAMES
            = {"Id", "Title", "Publish date", "Showing date"};
    
    private List<Movie> movies;

    public MoviesTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }

    public List<Movie> getMovies() {
        return movies;
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
                return movie.getPublishDate().toString();
            }
            case 3 -> {
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
