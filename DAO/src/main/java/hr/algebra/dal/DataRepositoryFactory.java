package hr.algebra.dal;

import hr.algebra.dal.sql.SQLRepository;

public class DataRepositoryFactory {
    private DataRepositoryFactory() {}
    
    private static IDataRepository repository = null;
    
    static {
        repository = new SQLRepository();
    }

    public static IDataRepository getRepository() {
        return repository;
    }  
}
