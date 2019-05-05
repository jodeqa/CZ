package com.jodeqa.cz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cashlez.android.sdk.managepassword.CLManagePasswordHandler;
import com.cashlez.android.sdk.managepassword.ICLManagePasswordService;

public class ChangePINActivity extends AppCompatActivity {

    EditText userName;
    ProgressBar changePinLoading;

    private CLManagePasswordHandler managePasswordHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);

        userName = findViewById(R.id.change_pin_user_name);
        changePinLoading = findViewById(R.id.loadingView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        managePasswordHandler = new CLManagePasswordHandler(getApplicationContext(), (ICLManagePasswordService) this);
    }

    public void goChangePin(View view) {
        managePasswordHandler.doChangePassword(userName.getText().toString().trim());
    }


}
