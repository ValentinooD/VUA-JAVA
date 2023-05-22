package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.User;
import java.util.List;
import java.util.Optional;

public class SQLRepository implements Repository {
    
    private UserSQLRepository userRepo;

    public SQLRepository() {
        userRepo = new UserSQLRepository();
    }

    @Override
    public List<User> selectUsers() throws Exception {
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
}
