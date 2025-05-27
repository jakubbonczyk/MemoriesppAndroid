package network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Interfejs API odpowiedzialny za operacje związane z planem zajęć.
 * Umożliwia pobieranie planów lekcji dla grup i nauczycieli, a także tworzenie nowych lekcji.
 */
public interface ScheduleApi {

    /**
     * Pobiera plan lekcji dla wskazanej grupy w zadanym przedziale czasowym.
     *
     * @param groupId identyfikator grupy
     * @param from    data początkowa zakresu (format: yyyy-MM-dd)
     * @param to      data końcowa zakresu (format: yyyy-MM-dd)
     * @return obiekt {@link Call} z listą lekcji przypisanych do grupy
     */
    @GET("api/schedules/group/{groupId}")
    Call<List<ScheduleResponseDTO>> getScheduleForGroup(
            @Path("groupId") int groupId,
            @Query("from") String from,
            @Query("to")   String to
    );

    /**
     * Pobiera plan lekcji dla wskazanego nauczyciela w zadanym przedziale czasowym.
     *
     * @param teacherId identyfikator nauczyciela
     * @param from      data początkowa zakresu (format: yyyy-MM-dd)
     * @param to        data końcowa zakresu (format: yyyy-MM-dd)
     * @return obiekt {@link Call} z listą lekcji przypisanych do nauczyciela
     */
    @GET("api/schedules/teacher/{teacherId}")
    Call<List<ScheduleResponseDTO>> getScheduleForTeacher(
            @Path("teacherId") int teacherId,
            @Query("from") String from,
            @Query("to")   String to
    );

    /**
     * Tworzy nową lekcję na podstawie przesłanych danych.
     *
     * @param dto obiekt zawierający szczegóły lekcji (dzień, godzina, grupa, nauczyciel, przedmiot)
     * @return obiekt {@link Call} z odpowiedzią zawierającą utworzoną lekcję
     */
    @POST("api/schedules")
    Call<ScheduleResponseDTO> createLesson(@Body ScheduleRequestDTO dto);
}
