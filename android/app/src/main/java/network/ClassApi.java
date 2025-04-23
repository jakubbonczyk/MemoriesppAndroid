package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ClassApi {
    @POST("/api/classes")
    Call<ClassResponse> createClass(@Body CreateClassRequest request);

    @GET("/api/classes")
    Call<List<ClassResponse>> getAllClasses();
}
