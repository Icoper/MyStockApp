package apps_x.com.mystockapp.ui.api.model.getutoken;

/**
 * Created by hp on 07.02.2018.
 */

public class GetUserTokenRequest {
    String action;
    Params params;

    public GetUserTokenRequest(String action, Params params) {
        this.action = action;
        this.params = params;
    }

    public static class Params {
        String phone;
        String password;

        public Params(String phone, String password) {
            this.phone = phone;
            this.password = password;
        }
    }

}
