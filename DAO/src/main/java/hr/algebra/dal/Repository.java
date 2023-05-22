package hr.algebra.dal;

import hr.algebra.model.User;
import java.util.List;
import java.util.Optional;

public interface Repository {
    public List<User> selectUsers() throws Exception;
    public Optional<User> selectUser(int id) throws Exception;
    public int createUser(User user) throws Exception;
    public boolean updateUser(int id, User user) throws Exception;
    public boolean deleteUser(int id) throws Exception;
    
    
    
    
    
    
}
