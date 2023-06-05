package hr.algebra.dal;

import hr.algebra.model.Movie;
import java.util.Collection;

public interface IDatabase {
    public boolean isConnected();
    
    public void fillDatabase(Collection<Movie> movies) throws Exception;
    public void clearDatabase() throws Exception;

    public <T> IDataRepository<T> getRepository(Class<T> type);
}
