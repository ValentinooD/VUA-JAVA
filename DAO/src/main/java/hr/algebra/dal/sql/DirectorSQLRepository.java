package hr.algebra.dal.sql;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Director;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import hr.algebra.dal.repos.IPersonRepository;

public class DirectorSQLRepository implements IPersonRepository<Director> {
    
    private static final String ID_DIRECTOR = "IDDirector";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    
    private static final String MOVIE_ID = "MovieID";
    private static final String DIRECTOR_ID = "DirectorID";
    
    private static final String SUCCESS = "Success";

    private static final String CREATE_DIRECTOR = "{ CALL createDirector (?,?,?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector (?,?,?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?) }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?) }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors }";
    
    private static final String SELECT_DIRECTORS_FOR_MOVIE = "{ CALL selectDirectorsForMovie (?) }";
    private static final String ADD_DIRECTORS_TO_MOVIE = "{ CALL addDirectorToMovie (?, ?, ?) }";
    
    @Override
    public void addToMovie(int movieId, Set<Director> directors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection()) {
            for (Director director : directors) {        
                // will return the ID even if it already exists
                int directorId = create(director);
                
                try (CallableStatement stmt = con.prepareCall(ADD_DIRECTORS_TO_MOVIE)) {
                    stmt.setInt(MOVIE_ID, movieId);
                    stmt.setInt(DIRECTOR_ID, directorId);
                    
                    stmt.registerOutParameter(SUCCESS, Types.INTEGER);
                    
                    stmt.execute();
                }
            }
        }
    }
    
    @Override
    public Collection<Director> getForMovie(int movieId) throws Exception {
        Set<Director> list = new HashSet<>(); // prevent duplicates
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS_FOR_MOVIE);) {

            stmt.setInt(MOVIE_ID, movieId);

            try (ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    list.add(new Director(
                                rs.getInt(ID_DIRECTOR), 
                                rs.getString(FIRST_NAME), 
                                rs.getString(LAST_NAME)));
                }
            }
        }

        return list;
    }
    
    
    @Override
    public Collection<Director> selectMultiple() throws Exception {
        List<Director> list = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTORS); 
                ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                list.add(new Director(
                            rs.getInt(ID_DIRECTOR), 
                            rs.getString(FIRST_NAME), 
                            rs.getString(LAST_NAME)));
            }
        }

        return list;
    }

    @Override
    public Optional<Director> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(SELECT_DIRECTOR);) {

            stmt.setInt(ID_DIRECTOR, id);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new Director(
                            rs.getInt(ID_DIRECTOR), 
                            rs.getString(FIRST_NAME), 
                            rs.getString(LAST_NAME)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int create(Director item) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR);) {
            stmt.setString(FIRST_NAME, item.getFirstName());
            stmt.setString(LAST_NAME, item.getLastName());
            
            stmt.registerOutParameter(ID_DIRECTOR, Types.INTEGER);

            stmt.executeUpdate();
            
            return stmt.getInt(ID_DIRECTOR);
        }
    }

    @Override
    public boolean update(int id, Director item) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR);) {
            stmt.setInt(ID_DIRECTOR, id);
            stmt.setString(FIRST_NAME, item.getFirstName());
            stmt.setString(LAST_NAME, item.getLastName());
            
            stmt.registerOutParameter(SUCCESS, Types.BIT);

            stmt.executeUpdate();

            return stmt.getBoolean(SUCCESS);
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR);) {

            stmt.setInt(ID_DIRECTOR, id);
            stmt.registerOutParameter(SUCCESS, Types.BIT);
            
            stmt.executeUpdate();
            
            return stmt.getBoolean(SUCCESS);
        }
    }
}
