package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroupApi {
    @POST("/api/groups")
    Call<GroupResponse> createGroup(@Body CreateGroupRequest req);

    @GET("/api/groups")
    Call<List<GroupResponse>> getAllGroups();

    @GET("/api/groups/{id}/teachers")
    Call<List<UserResponse>> getTeachersByGroup(@Path("id") int groupId);

    @GET("/api/users/{id}/groups")
    Call<List<GroupResponse>> getGroupsForUser(@Path("id") int userId);

    @GET("/api/groups/{groupId}/assignments")
    Call<List<AssignmentResponse>> getAssignmentsForGroup(@Path("groupId") int groupId);

    @POST("/api/assign/user/{userId}/group/{groupId}")
    Call<String> assignUserToGroup(
            @Path("userId") int userId,
            @Path("groupId") int groupId
    );
    @GET("api/groups/teacher/{id}")
    Call<List<GroupDTO>> getGroupsForTeacher(@Path("id") int teacherId);

    @GET("api/groups/{groupId}/teachers/{teacherId}/subject")
    Call<ClassDTO> getSubjectForGroupAndTeacher(
            @Path("groupId")   int groupId,
            @Path("teacherId") int teacherId
    );
}
