package hr.algebra.dal.sql;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import hr.algebra.dal.IDataRepository;
import hr.algebra.dal.IDatabase;


public class MovieSQLRepository implements IDataRepository<Movie> {
    private static final String ID_MOVIE = "IDMovie";
    private static final String MOVIE_ID = "MovieID";
    private static final String TITLE = "Title";
    private static final String DESCRIPTION = "Description";
    private static final String BANNER_PATH = "BannerPath";
    private static final String LINK = "Link";
    private static final String PUB_DATE = "PublishDate";
    private static final String SHOW_DATE = "ShowingDate";
    private static final String DIRECTOR_ID = "DirectorID";
    private static final String ID_DIRECTOR = "IDDirector";
    
    private static final String SUCCESS = "Success";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

    private final ActorSQLRepository actorRepo;
    private final DirectorSQLRepository directorRepo;
 
    public MovieSQLRepository(IDatabase database) {
        this.actorRepo = (ActorSQLRepository) database.getRepository(Actor.class);
        this.directorRepo = (DirectorSQLRepository) database.getRepository(Director.class);
    }
    
    @Override
    public Collection<Movie> selectMultiple() throws Exception {
        List<Movie> list = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES); 
                ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                Set<Director> directors = (HashSet<Director>) directorRepo.getForMovie(rs.getInt(ID_MOVIE));
                Set<Actor> actors = (HashSet<Actor>) actorRepo.getForMovie(rs.getInt(ID_MOVIE));
                
                Movie movie = new Movie(
                        rs.getInt(ID_MOVIE), 
                        rs.getString(TITLE), 
                        rs.getString(DESCRIPTION), 
                        directors, 
                        actors, 
                        rs.getString(BANNER_PATH), 
                        rs.getString(LINK), 
                        rs.getDate(PUB_DATE), 
                        rs.getDate(SHOW_DATE)
                );
                
                list.add(movie);
            }
        }

        return list;
    }

    @Override
    public Optional<Movie> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(SELECT_MOVIE);) {

            stmt.setInt(ID_MOVIE, id);

            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    Set<Director> directors = (HashSet<Director>) directorRepo.getForMovie(rs.getInt(ID_MOVIE));
                    Set<Actor> actors = (HashSet<Actor>) actorRepo.getForMovie(rs.getInt(ID_MOVIE));

                    Movie movie = new Movie(
                            rs.getInt(ID_MOVIE), 
                            rs.getString(TITLE), 
                            rs.getString(DESCRIPTION), 
                            directors, 
                            actors, 
                            rs.getString(BANNER_PATH), 
                            rs.getString(LINK), 
                            rs.getDate(PUB_DATE), 
                            rs.getDate(SHOW_DATE)
                    );
                    
                    return Optional.of(movie);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public int create(Movie item) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE);) {
            
            
            stmt.setString(TITLE, item.getTitle());
            stmt.setString(DESCRIPTION, item.getDescription());
            stmt.setString(BANNER_PATH, item.getBannerPath());
            stmt.setString(LINK, item.getLink());
            stmt.setDate(PUB_DATE, new Date(item.getPublishDate().getTime()));
            stmt.setDate(SHOW_DATE, new Date(item.getShowingDate().getTime()));
            
            stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);
            stmt.registerOutParameter(SUCCESS, Types.BIT);

            stmt.executeUpdate();

            if (!stmt.getBoolean(SUCCESS))
                throw new RuntimeException("Creation failed");
            
            actorRepo.addToMovie(stmt.getInt(ID_MOVIE), item.getActors());
            directorRepo.addToMovie(stmt.getInt(ID_MOVIE), item.getDirectors());
            
            
            
            return stmt.getInt(ID_MOVIE);
        }
    }

    @Override
    public boolean update(int id, Movie item) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE);) {
            stmt.setInt(ID_MOVIE, id);
            stmt.setString(TITLE, item.getTitle());
            stmt.setString(DESCRIPTION, item.getDescription());
            stmt.setString(BANNER_PATH, item.getBannerPath());
            stmt.setString(LINK, item.getLink());
            stmt.setDate(PUB_DATE, new Date(item.getPublishDate().getTime()));
            stmt.setDate(SHOW_DATE, new Date(item.getShowingDate().getTime()));
            
            stmt.registerOutParameter(SUCCESS, Types.BIT);

            stmt.executeUpdate();

            // remove all
            Collection<Actor> dbActors = actorRepo.getForMovie(item.getId());
            actorRepo.removeFromMovie(id, new HashSet<>(dbActors));
            
            Collection<Director> dbDirectors = directorRepo.getForMovie(item.getId());
            directorRepo.removeFromMovie(id, new HashSet<>(dbDirectors));
            
            // add all
            Collection<Actor> actors = actorRepo.getForMovie(id);
            Set<Actor> actorsToAdd = new HashSet<>(item.getActors());
            actorsToAdd.removeAll(actors);
            actorRepo.addToMovie(id, actorsToAdd);
            
            Collection<Director> directors = directorRepo.getForMovie(id);
            Set<Director> directorsToAdd = new HashSet<>(item.getDirectors());
            directorsToAdd.removeAll(directors);
            directorRepo.addToMovie(id, directorsToAdd);

            return stmt.getBoolean(SUCCESS);
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); 
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE);) {

            stmt.setInt(ID_MOVIE, id);
            
            stmt.executeUpdate();
            
            return true;
        }
    }
}
