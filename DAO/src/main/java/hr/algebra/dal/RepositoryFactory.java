package hr.algebra.dal;

import hr.algebra.dal.sql.SQLRepository;

public class RepositoryFactory {
    private RepositoryFactory() {}
    
    private static Repository repository = null;
    
    static {
        repository = new SQLRepository();
    }

    public static Repository getRepository() {
        return repository;
    }  
}
