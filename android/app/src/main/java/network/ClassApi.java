package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClassApi {
    @POST("/api/classes")
    Call<ClassResponse> createClass(@Body CreateClassRequest request);

    @GET("/api/classes")
    Call<List<ClassResponse>> getAllClasses();

    @GET("/api/assign/user/{userId}/group/{groupId}/classes")
    Call<List<ClassResponse>> getAssignedClasses(
            @Path("userId")  int userId,
            @Path("groupId") int groupId
    );

    // retrofit2 http mappings
    @POST("/api/assign/user/{userId}/group/{groupId}/class/{classId}")
    Call<String> assignTeacherToClass(
            @Path("userId")  int userId,
            @Path("groupId") int groupId,
            @Path("classId") int classId
    );


}
