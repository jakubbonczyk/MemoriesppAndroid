package network;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Interfejs API odpowiedzialny za operacje związane z użytkownikami.
 * Umożliwia pobieranie danych użytkowników, aktualizację profilu oraz zarządzanie zdjęciami profilowymi.
 */
public interface UserApi {

    /**
     * Wysyła zdjęcie profilowe użytkownika w formacie Base64.
     *
     * @param userId identyfikator użytkownika
     * @param image  mapa z kluczem "image" i wartością Base64 zdjęcia
     * @return obiekt {@link Call} bez odpowiedzi (status HTTP)
     */
    @PUT("/api/users/{id}/profile-image")
    Call<Void> uploadProfileImage(@Path("id") int userId, @Body Map<String, String> image);

    /**
     * Pobiera podstawowe dane użytkownika na podstawie jego identyfikatora.
     * Dane zwracane są jako mapa klucz-wartość.
     *
     * @param id identyfikator użytkownika
     * @return obiekt {@link Call} z mapą danych użytkownika
     */
    @GET("/api/users/{id}")
    Call<Map<String, String>> getUserById(@Path("id") int id);

    /**
     * Pobiera listę uczniów przypisanych do danej grupy.
     *
     * @param groupId identyfikator grupy
     * @return obiekt {@link Call} z listą użytkowników typu Student
     */
    @GET("/api/groups/{groupId}/students")
    Call<List<User>> getStudentsByGroup(@Path("groupId") int groupId);

    /**
     * Pobiera listę wszystkich nauczycieli w systemie.
     *
     * @return obiekt {@link Call} z listą użytkowników typu UserResponse (nauczyciele)
     */
    @GET("/api/users/teachers")
    Call<List<UserResponse>> getAllTeachers();

    /**
     * Pobiera listę wszystkich użytkowników w systemie.
     *
     * @return obiekt {@link Call} z listą użytkowników typu UserResponse
     */
    @GET("/api/users")
    Call<List<UserResponse>> getAllUsers();

    /**
     * Pobiera szczegółowe dane użytkownika na podstawie identyfikatora.
     *
     * @param id identyfikator użytkownika
     * @return obiekt {@link Call} z odpowiedzią typu EditUserResponse
     */
    @GET("/api/users/{id}")
    Call<EditUserResponse> getUser(@Path("id") int id);

    /**
     * Aktualizuje dane użytkownika na podstawie przesłanego żądania edycji.
     *
     * @param id  identyfikator użytkownika
     * @param req obiekt zawierający nowe dane użytkownika
     * @return obiekt {@link Call} z odpowiedzią tekstową (np. potwierdzenie aktualizacji)
     */
    @PUT("/api/users/{id}")
    Call<String> updateUser(@Path("id") int id, @Body EditUserRequest req);
}
