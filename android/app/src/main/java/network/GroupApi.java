package network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interfejs API odpowiedzialny za operacje związane z grupami.
 * Umożliwia tworzenie grup, pobieranie przypisanych użytkowników i nauczycieli,
 * a także zarządzanie przypisaniami do grup i przedmiotów.
 */
public interface GroupApi {

    /**
     * Tworzy nową grupę na podstawie przesłanych danych.
     *
     * @param req obiekt zawierający nazwę grupy
     * @return obiekt {@link Call} z odpowiedzią zawierającą utworzoną grupę
     */
    @POST("/api/groups")
    Call<GroupResponse> createGroup(@Body CreateGroupRequest req);

    /**
     * Pobiera listę wszystkich utworzonych grup.
     *
     * @return obiekt {@link Call} z listą grup
     */
    @GET("/api/groups")
    Call<List<GroupResponse>> getAllGroups();

    /**
     * Pobiera listę nauczycieli przypisanych do danej grupy.
     *
     * @param groupId identyfikator grupy
     * @return obiekt {@link Call} z listą nauczycieli
     */
    @GET("/api/groups/{id}/teachers")
    Call<List<UserResponse>> getTeachersByGroup(@Path("id") int groupId);

    /**
     * Pobiera grupy, do których przypisany jest dany użytkownik.
     *
     * @param userId identyfikator użytkownika
     * @return obiekt {@link Call} z listą grup użytkownika
     */
    @GET("/api/users/{id}/groups")
    Call<List<GroupResponse>> getGroupsForUser(@Path("id") int userId);

    /**
     * Pobiera przypisania nauczycieli do klas w ramach danej grupy.
     *
     * @param groupId identyfikator grupy
     * @return obiekt {@link Call} z listą przypisań nauczycieli
     */
    @GET("/api/groups/{groupId}/assignments")
    Call<List<AssignmentResponse>> getAssignmentsForGroup(@Path("groupId") int groupId);

    /**
     * Przypisuje użytkownika do wskazanej grupy.
     *
     * @param userId  identyfikator użytkownika
     * @param groupId identyfikator grupy
     * @return obiekt {@link Call} z odpowiedzią tekstową (np. potwierdzeniem)
     */
    @POST("/api/assign/user/{userId}/group/{groupId}")
    Call<String> assignUserToGroup(
            @Path("userId") int userId,
            @Path("groupId") int groupId
    );

    /**
     * Pobiera grupy, do których przypisany jest nauczyciel.
     *
     * @param teacherId identyfikator nauczyciela
     * @return obiekt {@link Call} z listą grup nauczyciela
     */
    @GET("api/groups/teacher/{id}")
    Call<List<GroupDTO>> getGroupsForTeacher(@Path("id") int teacherId);

    /**
     * Pobiera przedmiot, którego dany nauczyciel uczy w danej grupie.
     *
     * @param groupId   identyfikator grupy
     * @param teacherId identyfikator nauczyciela
     * @return obiekt {@link Call} z przedmiotem przypisanym nauczycielowi w grupie
     */
    @GET("api/groups/{groupId}/teachers/{teacherId}/subject")
    Call<ClassDTO> getSubjectForGroupAndTeacher(
            @Path("groupId")   int groupId,
            @Path("teacherId") int teacherId
    );
}
