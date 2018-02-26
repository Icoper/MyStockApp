package apps_x.com.mystockapp.ui.api.model.userbytoken;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samik on 26.02.2018.
 */

public class GetUserByTokenCont {
    @SerializedName("data")
    GetUserByTokenResponse user;

    public GetUserByTokenCont() {
    }

    public GetUserByTokenResponse getUser() {
        return user;
    }

}
