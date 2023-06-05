package hr.algebra.dal.repos;

import hr.algebra.dal.IDataRepository;
import hr.algebra.model.Person;
import java.util.Collection;
import java.util.Set;

public interface IPersonRepository<T extends Person> extends IDataRepository<T> {
    public void addToMovie(int movieId, Set<T> set) throws Exception;
    public Collection<T> getForMovie(int movieId) throws Exception;
}
