package hr.algebra.model;

public enum Role {
    ADMIN("Administrator"),
    USER("User");
    
    private final String prettyName;

    private Role(String prettyName) {
        this.prettyName = prettyName;
    }
    
    @Override
    public String toString() {
        return prettyName;
    }
}
