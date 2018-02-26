package apps_x.com.mystockapp.ui.api.model.getutoken;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hp on 07.02.2018.
 */

public class GetUserTokenResponse {
    @SerializedName("mess")
    String mess;
    @SerializedName("error")
    String error;
    @SerializedName("response")
    Response response;

    public GetUserTokenResponse() {
    }

    public String getMess() {
        return mess;
    }

    public String getError() {
        return error;
    }

    public Response getResponse() {
        return response;
    }

    public class Response {
        @SerializedName("success")
        String success;
        @SerializedName("errors")
        String errors;
        @SerializedName("usertoken")
        String userToken;


        public Response() {
        }

        public String getSuccess() {
            return success;
        }

        public String getErrors() {
            return errors;
        }

        public String getUserToken() {
            return userToken;
        }

    }
}
