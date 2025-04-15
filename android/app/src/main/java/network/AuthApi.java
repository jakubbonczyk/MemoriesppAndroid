package network;

import network.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.Map;

public interface AuthApi {
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body Map<String, String> credentials);
}
