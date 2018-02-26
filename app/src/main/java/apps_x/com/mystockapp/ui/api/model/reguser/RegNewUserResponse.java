package apps_x.com.mystockapp.ui.api.model.reguser;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hp on 07.02.2018.
 */

public class RegNewUserResponse {
    @SerializedName("mess")
    String mess;
    @SerializedName("error")
    String error;
    @SerializedName("response")
    Response response;

    public RegNewUserResponse() {
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
        ArrayList<String> errors;
        @SerializedName("usertoken")
        String userToken;
        @SerializedName("code")
        String code;

        public Response() {
        }

        public String getSuccess() {
            return success;
        }

        public ArrayList<String> getErrors() {
            return errors;
        }

        public String getUserToken() {
            return userToken;
        }

        public String getCode() {
            return code;
        }
    }
}
