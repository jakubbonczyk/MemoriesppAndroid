package network;

public class User {
    private int id;
    private String name;
    private String surname;
    private String role;

    // Getter do imienia i nazwiska razem (przydatny do Spinnera)
    public String getFullName() {
        return name + " " + surname;
    }

    // Gettery i settery
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
