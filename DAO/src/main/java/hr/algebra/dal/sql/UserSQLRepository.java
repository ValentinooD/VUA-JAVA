package hr.algebra.dal.sql;

import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class UserSQLRepository {
    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String IS_ADMINISTRATOR = "Administrator";
    
    private static final String SUCCESS = "Success";

    private static final String CREATE_USER = "{ CALL createUser (?,?,?,?,?,?) }";
    private static final String UPDATE_USER = "{ CALL updateUser (?,?,?,?,?,?) }";
    private static final String DELETE_USER = "{ CALL deleteUser (?) }";
    private static final String SELECT_USER = "{ CALL selectUser (?) }";
    private static final String SELECT_USERS = "{ CALL selectUsers }";
    
    public List<User> selectUsers() throws Exception {
        List<User> list = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_USERS); 
                ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                list.add(new User(
                            rs.getInt(ID_USER),
                            rs.getString(USERNAME), 
                            rs.getString(PASSWORD),
                            rs.getBoolean(IS_ADMINISTRATOR)));
            }
        }

        return list;
    }

    public Optional<User> selectUser(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USER);) {

            stmt.setInt(ID_USER, id);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt(ID_USER),
                            rs.getString(USERNAME), 
                            rs.getString(PASSWORD),
                            rs.getBoolean(IS_ADMINISTRATOR)));
                }
            }
        }
        return Optional.empty();
    }

    public int createUser(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER);) {
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD, user.getPassword());
            stmt.setBoolean(IS_ADMINISTRATOR, user.isAdministrator());
            
            stmt.registerOutParameter(SUCCESS, Types.BIT);
            stmt.registerOutParameter(ID_USER, Types.INTEGER);

            stmt.executeUpdate();

            if (stmt.getBoolean(SUCCESS))
                throw new RuntimeException("User already exists");
            
            return stmt.getInt(ID_USER);
        }
    }     

    public boolean updateUser(int id, User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_USER);) {
            stmt.setInt(ID_USER, id);
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD, user.getPassword());
            stmt.setBoolean(IS_ADMINISTRATOR, user.isAdministrator());
            
            stmt.registerOutParameter(SUCCESS, Types.BIT);

            stmt.executeUpdate();

            return stmt.getBoolean(SUCCESS);
        }
    }

    public boolean deleteUser(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_USER);) {

            stmt.setInt(ID_USER, id);
            stmt.registerOutParameter(SUCCESS, Types.BIT);
            
            stmt.executeUpdate();
            
            return stmt.getBoolean(SUCCESS);
        }
    }
}