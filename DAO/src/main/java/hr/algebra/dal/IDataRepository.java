package hr.algebra.dal;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.model.User;
import java.util.List;
import java.util.Optional;

public interface IDataRepository {
    public boolean isConnected();
    
    public List<User> selectUsers() throws Exception;
    public Optional<User> selectUser(int id) throws Exception;
    public int createUser(User user) throws Exception;
    public boolean updateUser(int id, User user) throws Exception;
    public boolean deleteUser(int id) throws Exception;
    public Optional<User> authenticate(String username, String password) throws Exception;
    
    public List<Movie> selectMovies() throws Exception;
    public Optional<Movie> selectMovie(int id) throws Exception;
    public int createMovie(Movie movie) throws Exception;
    public boolean updateMovie(int id, Movie movie) throws Exception;
    public boolean deleteMovie(int id ) throws Exception;
    
    public List<Actor> selectActors() throws Exception;
    public Optional<Actor> selectActor(int id) throws Exception;
    public int createActor(Actor actor) throws Exception;
    public boolean updateActor(int id, Actor actor) throws Exception;
    public boolean deleteActor(int id) throws Exception;
    public void addActorsToMovie(int movieId, List<Actor> actors) throws Exception;
    public List<Actor> getActorsForMovie(int movieId) throws Exception;
    
    public List<Director> selectDirectors() throws Exception;
    public Optional<Director> selectDirector(int id) throws Exception;
    public int createDirector(Director director) throws Exception;
    public boolean updateDirector(int id, Director director) throws Exception;
    public boolean deleteDirector(int id) throws Exception;
    
    public void fillDatabase(List<Movie> movies) throws Exception;
    public void clearDatabase() throws Exception;
}
