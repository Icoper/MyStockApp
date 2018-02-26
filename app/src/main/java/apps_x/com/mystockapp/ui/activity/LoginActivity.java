package apps_x.com.mystockapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apps_x.com.mystockapp.R;
import apps_x.com.mystockapp.ui.GlobalParam;
import apps_x.com.mystockapp.ui.api.HmacDigestWorker;
import apps_x.com.mystockapp.ui.api.Server;
import apps_x.com.mystockapp.ui.api.model.getutoken.GetUserTokenCont;
import apps_x.com.mystockapp.ui.api.model.getutoken.GetUserTokenRequest;
import apps_x.com.mystockapp.ui.api.model.getutoken.GetUserTokenResponse;
import apps_x.com.mystockapp.ui.api.model.HeaderRequest;
import apps_x.com.mystockapp.ui.db.SPHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = "LoginActivity";
    @BindView(R.id.al_sing_up_tv)
    TextView signUpTv;
    @BindView(R.id.al_sign_in_btn)
    Button signInBtn;
    @BindView(R.id.al_user_pass)
    EditText userPassEt;
    @BindView(R.id.al_user_phone)
    EditText userPhoneEt;
    @BindView(R.id.al_login_progress)
    View progressView;
    @BindView(R.id.al_login_form)
    View loginFormView;

    private String userPhone;
    private String userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        ButterKnife.bind(this);

        signInBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                userPhone = String.valueOf(userPhoneEt.getText());
                userPass = String.valueOf(userPassEt.getText());
                if (!userPass.isEmpty() || !userPhone.isEmpty()) {
                    getUserToken();
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signUpTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void getUserToken() {
        Log.d(LOG_TAG, "getUserToken is call");
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging); // <-- this is the important line!


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalParam.SERVER_URL) // Адрес сервера
                .addConverterFactory(GsonConverterFactory.create(gson)) // говорим ретрофиту что для сериализации необходимо использовать GSON
                .client(httpClient.build())
                .build();

        final GetUserTokenRequest userTokenRequest = new GetUserTokenRequest(
                GlobalParam.API_FUN_GET_USER_TOKEN, new GetUserTokenRequest.Params(userPhone, userPass));

        String jsonData = gson.toJson(userTokenRequest);
        jsonData.replaceAll("\\\\", "\"");

        String hash = "";
        hash = HmacDigestWorker.getHmacDigest(jsonData, getString(R.string.api_secret), "");

        HeaderRequest headerRequest = new HeaderRequest(
                GlobalParam.DEFAULT_ACCEPT_LANGUAGE,
                getString(R.string.api_key),
                hash);

        Server server = retrofit.create(Server.class);
        Call<GetUserTokenCont> call = server.getUserToken(
                headerRequest.getAccept(),
                headerRequest.getConnectType(),
                headerRequest.getContentLang(),
                getString(R.string.api_key),
                hash,
                userTokenRequest
        );
        call.enqueue(new Callback<GetUserTokenCont>() {
            @Override
            public void onResponse(Call<GetUserTokenCont> call, Response<GetUserTokenCont> response) {
                if (response.isSuccessful()) {
                    GetUserTokenResponse userTokenResponse = response.body().getUser();
                    GetUserTokenResponse.Response response1 = userTokenResponse.getResponse();
                    String token = response1.getUserToken();
                    SPHelper.setToken(LoginActivity.this, token);
                    Log.d(LOG_TAG, "getUserToken - successful");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<GetUserTokenCont> call, Throwable t) {
                Log.d(LOG_TAG, "getUserToken - failed " + t.getMessage());
            }
        });
    }
}

