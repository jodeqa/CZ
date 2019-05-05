package com.jodeqa.cz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class WelcomeActivity extends AppCompatActivity {
    ProgressBar progressBar;

    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        progressBar = findViewById(R.id.welcome_progress);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < progressBar.getMax()) {
                    progressStatus += 1;
                    updateProgressValue(progressStatus);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                }
            }
        }).start();
    }

    private void updateProgressValue(int progressStatus) {
        if (progressStatus == progressBar.getMax()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
