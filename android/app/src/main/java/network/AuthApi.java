package network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Interfejs API odpowiedzialny za operacje autoryzacyjne i uwierzytelniające.
 * Umożliwia logowanie, rejestrację użytkowników oraz inicjację resetowania hasła.
 */
public interface AuthApi {

    /**
     * Wysyła dane logowania do serwera w celu uwierzytelnienia użytkownika.
     *
     * @param credentials mapa zawierająca dane logowania (np. email i hasło)
     * @return obiekt {@link Call} z odpowiedzią zawierającą dane użytkownika i token
     */
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body Map<String, String> credentials);

    /**
     * Rejestruje nowego użytkownika na podstawie przesłanych danych.
     *
     * @param request obiekt zawierający dane nowego użytkownika
     * @return obiekt {@link Call} z odpowiedzią tekstową (np. potwierdzeniem)
     */
    @POST("/api/auth/register")
    Call<String> registerUser(@Body RegisterUserRequest request);

    /**
     * Inicjuje proces resetowania hasła – wysyła żądanie wygenerowania tokenu resetującego.
     *
     * @param body mapa zawierająca dane potrzebne do zainicjowania resetu (np. email)
     * @return obiekt {@link Call} bez odpowiedzi (tylko status HTTP)
     */
    @POST("/api/auth/request-password-reset")
    Call<Void> requestPasswordReset(@Body Map<String, String> body);
}
