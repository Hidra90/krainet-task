package by.krainet.task.model;

public enum Role {
    ADMIN,
    USER;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
