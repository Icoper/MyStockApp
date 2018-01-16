package apps_x.com.mystockapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import apps_x.com.mystockapp.R;

/**
 * Created by dmitriysamoilov on 15.01.18.
 */

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private TextView name;
    private TextView pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (TextView)findViewById(R.id.ma_username);
        pass = (TextView)findViewById(R.id.ma_userpass);
        Intent intent = getIntent();

        name.setText(intent.getStringExtra("name"));
        pass.setText(intent.getStringExtra("pass"));

    }
}
