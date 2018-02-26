package apps_x.com.mystockapp.ui.api.model.userbytoken;

import com.google.gson.annotations.SerializedName;

import apps_x.com.mystockapp.ui.api.model.User;

public class GetUserByTokenResponse {
    @SerializedName("mess")
    String mess;
    @SerializedName("error")
    String error;
    @SerializedName("response")
    Response response;

    public GetUserByTokenResponse() {
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
        @SerializedName("user")
        User user;


        public Response() {
        }

        public User getUser() {
            return user;
        }

        public String getSuccess() {
            return success;
        }

        public String getErrors() {
            return errors;
        }


    }
}
