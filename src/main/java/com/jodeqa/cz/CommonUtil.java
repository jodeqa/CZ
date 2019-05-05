package com.jodeqa.cz;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Taslim_Hartmann on 5/13/2017.
 */

public class CommonUtil {

    public static void displayInfo(final Activity context, final String message){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
