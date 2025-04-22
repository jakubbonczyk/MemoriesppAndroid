package network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @PUT("/api/users/{id}/profile-image")
    Call<Void> uploadProfileImage(@Path("id") int userId, @Body Map<String, String> image);

    @GET("/api/users/{id}")
    Call<Map<String, String>> getUserById(@Path("id") int id);

    @GET("/api/users/group/{groupId}/students")
    Call<List<User>> getStudentsByGroup(@Path("groupId") int groupId);


}
