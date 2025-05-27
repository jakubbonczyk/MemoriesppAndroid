package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interfejs API odpowiedzialny za operacje związane z klasami.
 * Umożliwia tworzenie klas, pobieranie listy klas oraz przypisywanie nauczycieli do klas.
 */
public interface ClassApi {

    /**
     * Tworzy nową klasę na podstawie przesłanych danych.
     *
     * @param request obiekt zawierający nazwę klasy oraz przypisanie do grupy
     * @return obiekt {@link Call} z odpowiedzią zawierającą utworzoną klasę
     */
    @POST("/api/classes")
    Call<ClassResponse> createClass(@Body CreateClassRequest request);

    /**
     * Pobiera listę wszystkich zdefiniowanych klas w systemie.
     *
     * @return obiekt {@link Call} z listą klas
     */
    @GET("/api/classes")
    Call<List<ClassResponse>> getAllClasses();

    /**
     * Pobiera klasy przypisane do konkretnego nauczyciela i grupy.
     *
     * @param userId identyfikator nauczyciela
     * @param groupId identyfikator grupy
     * @return obiekt {@link Call} z listą klas przypisanych nauczycielowi w ramach danej grupy
     */
    @GET("/api/assign/user/{userId}/group/{groupId}/classes")
    Call<List<ClassResponse>> getAssignedClasses(
            @Path("userId") int userId,
            @Path("groupId") int groupId
    );

    /**
     * Przypisuje nauczyciela do konkretnej klasy w ramach określonej grupy.
     *
     * @param userId identyfikator nauczyciela
     * @param groupId identyfikator grupy
     * @param classId identyfikator klasy
     * @return obiekt {@link Call} z odpowiedzią tekstową (np. potwierdzeniem przypisania)
     */
    @POST("/api/assign/user/{userId}/group/{groupId}/class/{classId}")
    Call<String> assignTeacherToClass(
            @Path("userId") int userId,
            @Path("groupId") int groupId,
            @Path("classId") int classId
    );
}
