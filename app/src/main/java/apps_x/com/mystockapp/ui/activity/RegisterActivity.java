package apps_x.com.mystockapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apps_x.com.mystockapp.R;
import apps_x.com.mystockapp.ui.GlobalParam;
import apps_x.com.mystockapp.ui.api.HmacDigestWorker;
import apps_x.com.mystockapp.ui.api.Server;
import apps_x.com.mystockapp.ui.api.model.HeaderRequest;
import apps_x.com.mystockapp.ui.api.model.reguser.RegNewUserRequest;
import apps_x.com.mystockapp.ui.api.model.reguser.RegNewUserResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = "RegisterActivity";

    private Retrofit retrofit;
    @BindView(R.id.ra_phone_et)
    EditText userPhoneEt;
    @BindView(R.id.ra_password_et)
    EditText userPassEt;
    @BindView(R.id.ra_user_name_et)
    EditText userNameEt;
    @BindView(R.id.ra_register_btn)
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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

        retrofit = new Retrofit.Builder()
                .baseUrl(GlobalParam.SERVER_URL) // Адрес сервера
                .addConverterFactory(GsonConverterFactory.create(gson)) // говорим ретрофиту что для сериализации необходимо использовать GSON
                .client(httpClient.build())
                .build();
        initializeUI();
    }

    private void initializeUI() {
        registerBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ra_register_btn:
                sendRegDataToServer();
                break;
        }
    }

    private void sendRegDataToServer() {
        if (String.valueOf(userPhoneEt.getText()).isEmpty() ||
                String.valueOf(userNameEt.getText()).isEmpty() ||
                String.valueOf(userPassEt.getText()).isEmpty()) {
            Toast.makeText(this, "Wrong data", Toast.LENGTH_SHORT).show();
        } else {
            final RegNewUserRequest regNewUserRequest = new RegNewUserRequest();
            regNewUserRequest.setPhone(String.valueOf(userPhoneEt.getText()));
            regNewUserRequest.setUsername(String.valueOf(userNameEt.getText()));
            regNewUserRequest.setPassword(String.valueOf(userPassEt.getText()));

            Gson gson = new Gson();
            String urlParams = "{ \"action\": \"" + GlobalParam.API_FUN_REG_NEW_USER + "\", \"params\" :";
            String jsonData = urlParams + gson.toJson(regNewUserRequest);
            String hash = "";

            hash = HmacDigestWorker.getHmacDigest(jsonData, getString(R.string.api_secret), "");

            HeaderRequest headerRequest = new HeaderRequest(GlobalParam.DEFAULT_ACCEPT_LANGUAGE, getString(R.string.api_key), hash);

            Server server = retrofit.create(Server.class);
            Call<RegNewUserResponse> call = server.registerNewUser(
                    headerRequest.getAccept(),
                    headerRequest.getConnectType(),
                    headerRequest.getContentLang(),
                    getString(R.string.api_key),
                    hash,
                    jsonData
            );
            call.enqueue(new Callback<RegNewUserResponse>() {
                @Override
                public void onResponse(Call<RegNewUserResponse> call, Response<RegNewUserResponse> response) {
                    if (response.isSuccessful()) {
                        RegNewUserResponse regNewUserResponse = response.body();
                        RegNewUserResponse.Response response1 = regNewUserResponse.getResponse();
                        String code = response1.getCode();
                        Log.d(LOG_TAG, regNewUserResponse.getMess());
                        Intent intent = new Intent(RegisterActivity.this, ValidateUserActivity.class);
                        intent.putExtra(GlobalParam.VALIDATION_PHONE, regNewUserRequest.getPhone());
                        intent.putExtra(GlobalParam.VALIDATION_CODE, code);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<RegNewUserResponse> call, Throwable t) {
                    Log.d(LOG_TAG, "regNewUser failed " + t.getMessage());
                }
            });

        }
    }


}
