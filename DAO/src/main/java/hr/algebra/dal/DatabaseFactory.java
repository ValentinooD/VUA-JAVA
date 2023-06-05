package hr.algebra.dal;

import hr.algebra.dal.sql.SQLDatabase;

public class DatabaseFactory {
    private DatabaseFactory() {}
    
    private static final IDatabase database;
    
    static {
        database = new SQLDatabase();
    }

    public static IDatabase getDatabase() {
        return database;
    }
} 
