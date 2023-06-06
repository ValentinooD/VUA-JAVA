package hr.algebra.dal.sql;

import hr.algebra.dal.repos.IUserRepository;
import hr.algebra.model.Role;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

public class UserSQLRepository implements IUserRepository<User> {
    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String ROLE = "Role";
    
    private static final String SUCCESS = "Success";

    private static final String CREATE_USER = "{ CALL createUser (?,?,?,?,?) }";
    private static final String UPDATE_USER = "{ CALL updateUser (?,?,?,?,?) }";
    private static final String DELETE_USER = "{ CALL deleteUser (?, ?) }";
    private static final String SELECT_USER = "{ CALL selectUser (?) }";
    private static final String SELECT_USERS = "{ CALL selectUsers }";
    private static final String AUTHENCIATE_USER = "{ CALL authenticateUser(?,?) }";
    
    /////////////////////////////////////////////////////
    
    @Override
    public Optional<User> authenticate(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(AUTHENCIATE_USER);) {

            stmt.setString(USERNAME, username);
            stmt.setString(PASSWORD, password);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt(ID_USER),
                            rs.getString(USERNAME), 
                            rs.getString(PASSWORD),
                            Role.valueOf(rs.getString(ROLE))));
                }
            }
        }
        return Optional.empty();
    }
    
    @Override
    public Collection<User> selectMultiple() throws Exception {
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
                            Role.valueOf(rs.getString(ROLE))));
            }
        }

        return list;
    }

    @Override
    public Optional<User> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_USER);) {

            stmt.setInt(ID_USER, id);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt(ID_USER),
                            rs.getString(USERNAME), 
                            rs.getString(PASSWORD),
                            Role.valueOf(rs.getString(ROLE))));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int create(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER);) {
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD, user.getPassword());
            stmt.setString(ROLE, user.getRole().name());
            
            stmt.registerOutParameter(SUCCESS, Types.BIT);
            stmt.registerOutParameter(ID_USER, Types.INTEGER);

            stmt.executeUpdate();

            if (!stmt.getBoolean(SUCCESS))
                throw new RuntimeException("User already exists");
            
            return stmt.getInt(ID_USER);
        }
    }     

    @Override
    public boolean update(int id, User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_USER);) {
            stmt.setInt(ID_USER, id);
            stmt.setString(USERNAME, user.getUsername());
            stmt.setString(PASSWORD, user.getPassword());
            stmt.setString(ROLE, user.getRole().name());
            
            stmt.registerOutParameter(SUCCESS, Types.BIT);

            stmt.executeUpdate();

            return stmt.getBoolean(SUCCESS);
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_USER);) {

            stmt.setInt(ID_USER, id);
            stmt.registerOutParameter(SUCCESS, Types.BIT);
            
            stmt.executeUpdate();
            
            return stmt.getBoolean(SUCCESS);
        }
    }
}
