package network;

import com.google.gson.annotations.SerializedName;

public class RegisterUserRequest {
    @SerializedName("login")
    private final String login;

    @SerializedName("password")
    private final String password;

    @SerializedName("name")
    private final String name;

    @SerializedName("surname")
    private final String surname;

    @SerializedName("role")
    private final String role;

    @SerializedName("groupId")
    private final Integer groupId;

    public RegisterUserRequest(String login,
                               String password,
                               String name,
                               String surname,
                               String role,
                               Integer groupId) {
        this.login    = login;
        this.password = password;
        this.name     = name;
        this.surname  = surname;
        this.role     = role;
        this.groupId  = groupId;
    }

    public String   getLogin()    { return login; }
    public String   getPassword() { return password; }
    public String   getName()     { return name; }
    public String   getSurname()  { return surname; }
    public String   getRole()     { return role; }
    public Integer  getGroupId()  { return groupId; }
}
