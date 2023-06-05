package hr.algebra.dal.sql;

import hr.algebra.dal.IDataRepositoryCRUD;
import hr.algebra.model.Actor;
import hr.algebra.model.Role;
import hr.algebra.model.User;
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



public class ActorSQLRepository implements IDataRepositoryCRUD<Actor> {

    private static final String ID_ACTOR = "IDActor";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    
    private static final String MOVIE_ID = "MovieID";
    private static final String ACTOR_ID = "ActorID";
    
    private static final String SUCCESS = "Success";

    private static final String CREATE_ACTOR = "{ CALL createActor (?,?,?) }";
    private static final String UPDATE_ACTOR = "{ CALL updateActor (?,?,?,?) }";
    private static final String DELETE_ACTOR = "{ CALL deleteActor (?) }";
    private static final String SELECT_ACTOR = "{ CALL selectActor (?) }";
    private static final String SELECT_ACTORS = "{ CALL selectActors }";
    
    private static final String SELECT_ACTORS_FOR_MOVIE = "{ CALL selectActorsForMovie (?) }";
    private static final String ADD_ACTORS_TO_MOVIE = "{ CALL addActorToMovie (?, ?, ?) }";
    
    public void addActorsToMovie(int movieId, Set<Actor> actors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection()) {
            for (Actor actor : actors) {        
                // will return the ID even if it already exists
                int actorId = create(actor);
                
                try (CallableStatement stmt = con.prepareCall(ADD_ACTORS_TO_MOVIE)) {
                    stmt.setInt(MOVIE_ID, movieId);
                    stmt.setInt(ACTOR_ID, actorId);
                    
                    stmt.registerOutParameter(SUCCESS, Types.INTEGER);
                    
                    stmt.execute();
                }
            }
        }
    }
    
    public Collection<Actor> getActorsForMovie(int movieId) throws Exception {
        Set<Actor> list = new HashSet<>(); // prevent duplicates
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(SELECT_ACTORS_FOR_MOVIE);) {

            stmt.setInt(MOVIE_ID, movieId);

            try (ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    list.add(new Actor(
                                rs.getInt(ID_ACTOR), 
                                rs.getString(FIRST_NAME), 
                                rs.getString(LAST_NAME)));
                }
            }
        }

        return list;
    }
    
    @Override
    public Collection<Actor> selectMultiple() throws Exception {
        List<Actor> list = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACTORS); 
                ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                list.add(new Actor(
                            rs.getInt(ID_ACTOR), 
                            rs.getString(FIRST_NAME), 
                            rs.getString(LAST_NAME)));
            }
        }

        return list;
    }

    @Override
    public Optional<Actor> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(SELECT_ACTOR);) {

            stmt.setInt(ID_ACTOR, id);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_ACTOR), 
                            rs.getString(FIRST_NAME), 
                            rs.getString(LAST_NAME)));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int create(Actor item) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(CREATE_ACTOR);) {
            stmt.setString(FIRST_NAME, item.getFirstName());
            stmt.setString(LAST_NAME, item.getLastName());
            
            stmt.registerOutParameter(ID_ACTOR, Types.INTEGER);

            stmt.executeUpdate();
            
            return stmt.getInt(ID_ACTOR);
        }
    }

    @Override
    public boolean update(int id, Actor item) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(UPDATE_ACTOR);) {
            stmt.setInt(ID_ACTOR, id);
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
                CallableStatement stmt = con.prepareCall(DELETE_ACTOR);) {

            stmt.setInt(ID_ACTOR, id);
            stmt.registerOutParameter(SUCCESS, Types.BIT);
            
            stmt.executeUpdate();
            
            return stmt.getBoolean(SUCCESS);
        }
    }

   
}
