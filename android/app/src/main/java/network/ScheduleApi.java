package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleApi {

    @POST("/api/schedules")
    Call<ScheduleResponseDTO> createLesson(@Body ScheduleRequestDTO dto);

    @GET("/api/schedules/group/{groupId}")
    Call<List<ScheduleResponseDTO>> getScheduleForGroup(
            @Path("groupId") int groupId,
            @Query("from") String from,
            @Query("to")   String to
    );
}

