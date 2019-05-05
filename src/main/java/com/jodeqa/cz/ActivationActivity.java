package com.jodeqa.cz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cashlez.android.sdk.CLErrorResponse;
import com.cashlez.android.sdk.CLResponse;
import com.cashlez.android.sdk.activation.CLActivationHandler;
import com.cashlez.android.sdk.activation.ICLActivationService;

import static com.jodeqa.cz.CommonUtil.displayInfo;

public class ActivationActivity extends AppCompatActivity implements ICLActivationService {

    EditText activationCode;
    ProgressBar activationLoading;

    private CLActivationHandler managePasswordFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        activationCode = findViewById(R.id.activation_code);
        activationLoading  = findViewById(R.id.loadingView);
        managePasswordFlow = new CLActivationHandler(getApplicationContext(), this);
    }

    public void goActivate(View view) {
        managePasswordFlow.doActivate(activationCode.getText().toString().trim());
    }

    @Override
    public void onActivationSuccess(CLResponse clResponse) {
        displayInfo(this, clResponse.getMessage());
    }

    @Override
    public void onActivationError(CLErrorResponse clErrorResponse) {
        Log.d("error", clErrorResponse.toString());
    }
}
