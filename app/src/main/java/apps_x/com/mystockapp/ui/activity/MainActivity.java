package apps_x.com.mystockapp.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import apps_x.com.mystockapp.R;
import apps_x.com.mystockapp.ui.GlobalParam;
import apps_x.com.mystockapp.ui.api.HmacDigestWorker;
import apps_x.com.mystockapp.ui.api.Server;
import apps_x.com.mystockapp.ui.api.model.HeaderRequest;
import apps_x.com.mystockapp.ui.api.model.User;
import apps_x.com.mystockapp.ui.api.model.getutoken.GetUserTokenCont;
import apps_x.com.mystockapp.ui.api.model.getutoken.GetUserTokenRequest;
import apps_x.com.mystockapp.ui.api.model.getutoken.GetUserTokenResponse;
import apps_x.com.mystockapp.ui.api.model.userbytoken.GetUserByTokenCont;
import apps_x.com.mystockapp.ui.api.model.userbytoken.GetUserByTokenRequest;
import apps_x.com.mystockapp.ui.api.model.userbytoken.GetUserByTokenResponse;
import apps_x.com.mystockapp.ui.db.SPHelper;
import apps_x.com.mystockapp.ui.fragm.AddItemFragm;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String LOG_TAG = "MainActivity";

    private TextView navUserName;
    private FragmentTransaction fragmentTransaction;
    private NavigationView navigationView;
    // Fragments
    private AddItemFragm addItemFragm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SPHelper.getToken(this).isEmpty()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        getUserData();
        initialize();
        initializeFragment();

    }

    private void initializeFragment() {
        addItemFragm = new AddItemFragm();
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, addItemFragm);
        fragmentTransaction.commit();
    }

    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);

        navUserName = (TextView) hView.findViewById(R.id.nv_user_name);
        navUserName.setText("");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getUserData() {
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

        GetUserByTokenRequest userTokenRequest = new GetUserByTokenRequest(
                GlobalParam.API_FUN_GET_USER_BY_T0KEN,
                new GetUserByTokenRequest.Params(SPHelper.getToken(this)));

        String jsonData = gson.toJson(userTokenRequest);
        jsonData.replaceAll("\\\\", "\"");

        String hash = "";
        hash = HmacDigestWorker.getHmacDigest(jsonData, getString(R.string.api_secret), "");

        HeaderRequest headerRequest = new HeaderRequest(
                GlobalParam.DEFAULT_ACCEPT_LANGUAGE,
                getString(R.string.api_key),
                hash);

        Server server = retrofit.create(Server.class);
        Call<GetUserByTokenCont> call = server.getUserByToken(
                headerRequest.getAccept(),
                headerRequest.getConnectType(),
                headerRequest.getContentLang(),
                getString(R.string.api_key),
                hash,
                userTokenRequest
        );
        call.enqueue(new Callback<GetUserByTokenCont>() {
            @Override
            public void onResponse(Call<GetUserByTokenCont> call, Response<GetUserByTokenCont> response) {
                if (response.isSuccessful()) {
                    User user = response.body().getUser().getResponse().getUser();
                    navUserName.setText(user.getUserName() + "($ " + user.getBalance() + ")");
                    Log.d(LOG_TAG, "getUserByToken - successful");
                }
            }

            @Override
            public void onFailure(Call<GetUserByTokenCont> call, Throwable t) {
                Log.d(LOG_TAG, "getUserByToken - failed " + t.getMessage());
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_item) {
            fragmentTransaction.replace(R.id.fragment_container, addItemFragm);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_my_rating) {
            Toast.makeText(this, "In Develop", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_my_sales) {
            Toast.makeText(this, "In Develop", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_my_stock) {
            Toast.makeText(this, "In Develop", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_search_other_stock) {
            Toast.makeText(this, "In Develop", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_stock_for_sale) {
            Toast.makeText(this, "In Develop", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_logout) {
            System.exit(0);
            Toast.makeText(this, "In Develop", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
