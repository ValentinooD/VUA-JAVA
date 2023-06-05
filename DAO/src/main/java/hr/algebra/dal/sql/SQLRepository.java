package hr.algebra.dal.sql;

import hr.algebra.dal.IDataRepository;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.User;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SQLRepository implements IDataRepository {
    
    private UserSQLRepository userRepo;
    private ActorSQLRepository actorRepo;
    private DirectorSQLRepository directorRepo;
    private MovieSQLRepository movieRepo;

    public SQLRepository() {
        userRepo = new UserSQLRepository();
        actorRepo = new ActorSQLRepository();
        directorRepo = new DirectorSQLRepository();
        movieRepo = new MovieSQLRepository(actorRepo, directorRepo);
    }

    @Override
    public boolean isConnected() {
        try {
            return DataSourceSingleton.getInstance().getConnection().isValid(1000);
        } catch (SQLException ex) {
            return false;
        }
    }
    
    // Users
    
    @Override
    public Collection<User> selectUsers() throws Exception {
        return userRepo.selectUsers();
    }

    @Override
    public Optional<User> selectUser(int id) throws Exception {
        return userRepo.selectUser(id);
    }

    @Override
    public int createUser(User user) throws Exception {
        return userRepo.createUser(user);
    }

    @Override
    public boolean updateUser(int id, User user) throws Exception {
        return userRepo.updateUser(id, user);
    }

    @Override
    public boolean deleteUser(int id) throws Exception {
        return userRepo.deleteUser(id);
    }

    @Override
    public Optional<User> authenticate(String username, String password) throws Exception {
        return userRepo.authenticate(username, password);
    }
    
    /// Movies

    @Override
    public Collection<Movie> selectMovies() throws Exception {
        return movieRepo.selectMultiple();
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        return movieRepo.select(id);
    }

    @Override
    public int createMovie(Movie movie) throws Exception {
        return movieRepo.create(movie);
    }

    @Override
    public boolean updateMovie(int id, Movie movie) throws Exception {
        return movieRepo.update(id, movie);
    }

    @Override
    public boolean deleteMovie(int id) throws Exception {
        return movieRepo.delete(id);
    }

    // Actors
    
    @Override
    public Collection<Actor> selectActors() throws Exception {
        return actorRepo.selectMultiple();
    }

    @Override
    public Optional<Actor> selectActor(int id) throws Exception {
        return actorRepo.select(id);
    }

    @Override
    public int createActor(Actor actor) throws Exception {
        return actorRepo.create(actor);
    }

    @Override
    public boolean updateActor(int id, Actor actor) throws Exception {
        return actorRepo.update(id, actor);
    }

    @Override
    public boolean deleteActor(int id) throws Exception {
        return actorRepo.delete(id);
    }

    @Override
    public void addActorsToMovie(int movieId, Set<Actor> actors) throws Exception {
        actorRepo.addActorsToMovie(movieId, actors);
    }

    @Override
    public Collection<Actor> getActorsForMovie(int movieId) throws Exception {
        return actorRepo.getActorsForMovie(movieId);
    }

    // Directors
    
    @Override
    public Collection<Director> selectDirectors() throws Exception {
        return directorRepo.selectMultiple();
    }

    @Override
    public Optional<Director> selectDirector(int id) throws Exception {
        return directorRepo.select(id);
    }

    @Override
    public int createDirector(Director director) throws Exception {
        return directorRepo.create(director);
    }

    @Override
    public boolean updateDirector(int id, Director director) throws Exception {
        return directorRepo.update(id, director);
    }

    @Override
    public boolean deleteDirector(int id) throws Exception {
        return directorRepo.delete(id);
    }
    
    // Database
    @Override
    public void fillDatabase(Collection<Movie> movies) throws Exception {
        for (Movie movie : movies) {
            createMovie(movie);
        }
    }

    @Override
    public void clearDatabase() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
