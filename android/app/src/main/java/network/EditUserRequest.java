package network;

public class EditUserRequest {
    private String login;
    private String name;
    private String surname;
    private String role;

    public EditUserRequest(String login, String name, String surname, String role) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
