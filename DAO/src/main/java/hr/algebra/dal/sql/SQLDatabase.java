package hr.algebra.dal.sql;

import hr.algebra.dal.IDataRepository;
import hr.algebra.model.Movie;
import java.sql.SQLException;
import java.util.Collection;
import hr.algebra.dal.IDatabase;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.User;
import java.util.HashMap;

public class SQLDatabase implements IDatabase {
    
    private HashMap<Class<?>, IDataRepository<?>> repos;
    
    private UserSQLRepository userRepo;
    private ActorSQLRepository actorRepo;
    private DirectorSQLRepository directorRepo;
    private MovieSQLRepository movieRepo;

    public SQLDatabase() {
        this.repos = new HashMap<>();
        
        repos.put(User.class, new UserSQLRepository());
        repos.put(Actor.class, new ActorSQLRepository());
        repos.put(Director.class, new DirectorSQLRepository());
        repos.put(Movie.class, new MovieSQLRepository(this));
    }

    @Override
    public boolean isConnected() {
        try {
            return DataSourceSingleton.getInstance().getConnection().isValid(1000);
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public <T> IDataRepository<T> getRepository(Class<T> clazz) {
        return (IDataRepository<T>) repos.get(clazz);
    }
    
    @Override
    public void fillDatabase(Collection<Movie> movies) throws Exception {
        for (Movie movie : movies) {
            getRepository(Movie.class).create(movie);
        }
    }

    @Override
    public void clearDatabase() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
