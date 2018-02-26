package apps_x.com.mystockapp.ui.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import apps_x.com.mystockapp.ui.api.model.reguser.RegNewUserResponse;

/**
 * Created by hp on 07.02.2018.
 */

public class ValidateUserResponse {
    @SerializedName("mess")
    String mess;
    @SerializedName("error")
    String error;
    @SerializedName("response")
    RegNewUserResponse.Response response;

    public ValidateUserResponse() {
    }

    public String getMess() {
        return mess;
    }

    public String getError() {
        return error;
    }

    public RegNewUserResponse.Response getResponse() {
        return response;
    }

    class Response {
        @SerializedName("success")
        String success;
        @SerializedName("errors")
        ArrayList<String> errors;
        @SerializedName("usertoken")
        String userToken;


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


    }
}
