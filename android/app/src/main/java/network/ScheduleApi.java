package network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface ScheduleApi {
    @GET("api/schedules/group/{groupId}")
    Call<List<ScheduleResponseDTO>> getScheduleForGroup(
            @Path("groupId") int groupId,
            @Query("from") String from,
            @Query("to")   String to
    );

    @GET("api/schedules/teacher/{teacherId}")
    Call<List<ScheduleResponseDTO>> getScheduleForTeacher(
            @Path("teacherId") int teacherId,
            @Query("from") String from,
            @Query("to")   String to
    );

    @POST("api/schedules")
    Call<ScheduleResponseDTO> createLesson(@Body ScheduleRequestDTO dto);
}
