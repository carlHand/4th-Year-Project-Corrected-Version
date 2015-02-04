package com.projectyr4x00091174.carl.traingain;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by carl on 20/11/2014.
 */
public class Message {
    public static void message(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
