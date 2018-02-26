package apps_x.com.mystockapp.ui.api.model.userbytoken;

import com.google.gson.annotations.SerializedName;


/**
 * Created by samik on 26.02.2018.
 */

public class GetUserByTokenRequest {
    String action;
    Params params;

    public GetUserByTokenRequest(String action, Params params) {
        this.action = action;
        this.params = params;
    }

    public static class Params {
        @SerializedName("usertoken")
        String userToken;

        public Params(String uToken) {
            this.userToken = uToken;
        }
    }

}
