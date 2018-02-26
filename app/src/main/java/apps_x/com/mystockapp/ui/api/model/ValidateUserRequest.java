package apps_x.com.mystockapp.ui.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hp on 07.02.2018.
 */

public class ValidateUserRequest {
    String userToken;
    String code;

    public ValidateUserRequest(String userToken, String code) {
        this.userToken = userToken;
        this.code = code;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
