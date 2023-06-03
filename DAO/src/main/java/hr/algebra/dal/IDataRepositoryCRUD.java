package hr.algebra.dal;

import java.util.List;
import java.util.Optional;

public interface IDataRepositoryCRUD<T> {
    public List<T> selectMultiple() throws Exception;
    public Optional<T> select(int id) throws Exception;
    public int create(T item) throws Exception;
    public boolean update(int id, T item) throws Exception;
    public boolean delete(int id) throws Exception;
}
