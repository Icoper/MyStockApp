package apps_x.com.mystockapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import apps_x.com.mystockapp.R;
import apps_x.com.mystockapp.ui.GlobalParam;

public class ValidateUserActivity extends AppCompatActivity {
    private final String LOG_TAG = "ValidateUserActivity";
    private Button validateBtn;
    private Button resendCodeBtn;
    private TextView userPhoneTv;
    private EditText validateCodeEt;

    private String userPhone = "";
    private String validateCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
        Intent intent = getIntent();
        userPhone = intent.getStringExtra(GlobalParam.VALIDATION_PHONE);
        validateCode = intent.getStringExtra(GlobalParam.VALIDATION_CODE);

        initUI();
    }

    private void initUI() {
        validateCodeEt = (EditText) findViewById(R.id.av_validate_code);
        userPhoneTv = (TextView) findViewById(R.id.av_user_phone);
        userPhoneTv.setText(userPhone);
        validateBtn = (Button) findViewById(R.id.av_verify_btn);
        resendCodeBtn = (Button) findViewById(R.id.av_resend_sms_btn);
        resendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ValidateUserActivity.this, "in develop", Toast.LENGTH_SHORT).show();
            }
        });
        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(validateCodeEt.getText()).equals(validateCode)) {
                    startActivity(new Intent(ValidateUserActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(ValidateUserActivity.this, "Invalid code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
