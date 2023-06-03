package hr.algebra.dal.sql;

import hr.algebra.dal.IDataRepositoryCRUD;
import hr.algebra.model.Director;
import hr.algebra.model.Director;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class DirectorSQLRepository implements IDataRepositoryCRUD<Director> {
    
    private static final String ID_DIRECTOR = "IDDirector";
    private static final String DIRECTOR_ID = "DirectorID";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    
    private static final String SUCCESS = "Success";

    private static final String CREATE_DIRECTOR = "{ CALL createDirector (?,?,?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector (?,?,?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?) }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?) }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors }";
    
    @Override
    public List<Director> selectMultiple() throws Exception {
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
