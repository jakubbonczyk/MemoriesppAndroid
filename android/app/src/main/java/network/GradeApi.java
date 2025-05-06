package network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GradeApi {
    @POST("/api/grades")
    Call<Void> addGrade(@Body Map<String, Object> gradeData);

    @GET("/api/grades/student/{studentId}")
    Call<List<Grade>> getGradesForStudent(@Path("studentId") int studentId);

    @GET("/api/class/student/{userId}/subjects")
    Call<List<ClassResponse>> getSubjectsForStudent(@Path("userId") int userId);


}
