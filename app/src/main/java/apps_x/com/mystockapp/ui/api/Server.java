package apps_x.com.mystockapp.ui.api;

import apps_x.com.mystockapp.ui.api.model.getutoken.GetUserTokenCont;
import apps_x.com.mystockapp.ui.api.model.getutoken.GetUserTokenRequest;
import apps_x.com.mystockapp.ui.api.model.reguser.RegNewUserResponse;
import apps_x.com.mystockapp.ui.api.model.ValidateUserResponse;
import apps_x.com.mystockapp.ui.api.model.userbytoken.GetUserByTokenCont;
import apps_x.com.mystockapp.ui.api.model.userbytoken.GetUserByTokenRequest;
import apps_x.com.mystockapp.ui.api.model.userbytoken.GetUserByTokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by hp on 06.02.2018.
 */

public interface Server {

    @POST("/api/registerNewUser")
    Call<RegNewUserResponse> registerNewUser(
            @Header("Accept") String accept,
            @Header("Content-Type") String connectType,
            @Header("Content-Language") String contentLang,
            @Header("apikey") String apiKey,
            @Header("hash") String hash,

            @Body String json);

    @POST("/api/validateUser")
    Call<ValidateUserResponse> validateUser(
            @Header("Accept") String accept,
            @Header("Content-Type") String connectType,
            @Header("Content-Language") String contentLang,
            @Header("apikey") String apiKey,
            @Header("hash") String hash,

            @Body String json);

    @POST("/api/")
    Call<GetUserTokenCont> getUserToken(
            @Header("Accept") String accept,
            @Header("Content-Type") String connectType,
            @Header("Content-Language") String contentLang,
            @Header("apikey") String apiKey,
            @Header("hash") String hash,

            @Body GetUserTokenRequest requestBody);

    @POST("/api/")
    Call<GetUserByTokenCont> getUserByToken(
            @Header("Accept") String accept,
            @Header("Content-Type") String connectType,
            @Header("Content-Language") String contentLang,
            @Header("apikey") String apiKey,
            @Header("hash") String hash,

            @Body GetUserByTokenRequest requestBody);

}
