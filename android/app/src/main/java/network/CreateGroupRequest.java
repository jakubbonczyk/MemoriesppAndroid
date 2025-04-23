// app/src/main/java/com/example/memoriespp/network/CreateGroupRequest.java
package network;

public class CreateGroupRequest {
    private String groupName;
    public CreateGroupRequest(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
