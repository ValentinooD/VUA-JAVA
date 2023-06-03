package hr.algebra.dal.sql;

import hr.algebra.dal.IDataRepositoryCRUD;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;


public class MovieSQLRepository implements IDataRepositoryCRUD<Movie> {
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

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";

    private ActorSQLRepository actorRepo;
    private DirectorSQLRepository directorRepo;

    public MovieSQLRepository(ActorSQLRepository actorRepo, DirectorSQLRepository directorRepo) {
        this.actorRepo = actorRepo;
        this.directorRepo = directorRepo;
    }
    
    @Override
    public List<Movie> selectMultiple() throws Exception {
        List<Movie> list = new ArrayList<>();

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES); 
                ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                Optional<Director> director = directorRepo.select(rs.getInt(DIRECTOR_ID));
                if (director.isEmpty()) continue;
                List<Actor> actors = actorRepo.getActorsForMovie(rs.getInt(ID_MOVIE));
                
                Movie movie = new Movie(
                        rs.getInt(ID_MOVIE), 
                        rs.getString(TITLE), 
                        rs.getString(DESCRIPTION), 
                        director.get(), 
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
                    Optional<Director> director = directorRepo.select(rs.getInt(DIRECTOR_ID));
                    if (director.isEmpty()) return Optional.empty();
                    
                    List<Actor> actors = actorRepo.getActorsForMovie(rs.getInt(ID_MOVIE));

                    Movie movie = new Movie(
                            rs.getInt(ID_MOVIE), 
                            rs.getString(TITLE), 
                            rs.getString(DESCRIPTION), 
                            director.get(), 
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
            
            if (!directorRepo.select(item.getDirector().getId()).isPresent()) {
                item.getDirector().setId(directorRepo.create(item.getDirector()));
            }
            
            stmt.setString(TITLE, item.getTitle());
            stmt.setString(DESCRIPTION, item.getDescription());
            stmt.setString(BANNER_PATH, item.getBannerPath());
            stmt.setString(LINK, item.getLink());
            stmt.setDate(PUB_DATE, new Date(item.getPubishDate().getTime()));
            stmt.setDate(SHOW_DATE, new Date(item.getShowingDate().getTime()));
            stmt.setInt(ID_DIRECTOR, item.getDirector().getId());
            
            stmt.registerOutParameter(ID_MOVIE, Types.INTEGER);
            stmt.registerOutParameter(SUCCESS, Types.BIT);

            stmt.executeUpdate();

            if (!stmt.getBoolean(SUCCESS))
                throw new RuntimeException("Creation failed");
            
            List<Actor> actors = item.getActors();
            actorRepo.addActorsToMovie(stmt.getInt(ID_MOVIE), actors);
            
            return stmt.getInt(ID_MOVIE);
        }
    }

    @Override
    public boolean update(int id, Movie item) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
