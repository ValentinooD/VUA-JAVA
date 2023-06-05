package hr.algebra.dal.repos;

import hr.algebra.dal.IDataRepository;
import hr.algebra.model.User;
import java.util.Optional;

public interface IUserRepository<T extends User> extends IDataRepository<T> {
    public Optional<User> authenticate(String username, String password) throws Exception;
}
