package network;

import com.google.gson.annotations.SerializedName;

public class CreateClassRequest {
    @SerializedName("className")
    private final String className;

    public CreateClassRequest(String className) {
        this.className = className;
    }

    public String getClassName() { return className; }
}
