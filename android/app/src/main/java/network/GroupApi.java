// app/src/main/java/com/example/memoriespp/network/GroupApi.java
package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GroupApi {
    @POST("/api/groups")
    Call<GroupResponse> createGroup(@Body CreateGroupRequest req);

    @GET("/api/groups")
    Call<List<GroupResponse>> getAllGroups();
}
