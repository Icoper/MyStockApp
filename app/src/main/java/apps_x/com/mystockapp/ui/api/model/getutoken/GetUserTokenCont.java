package apps_x.com.mystockapp.ui.api.model.getutoken;

import com.google.gson.annotations.SerializedName;


/**
 * Created by samik on 25.02.2018.
 */

public class GetUserTokenCont {
    @SerializedName("data")
    GetUserTokenResponse user;

    public GetUserTokenCont() {
    }

    public GetUserTokenResponse getUser() {
        return user;
    }

}
