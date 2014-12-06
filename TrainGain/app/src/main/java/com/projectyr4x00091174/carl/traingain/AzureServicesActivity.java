package com.projectyr4x00091174.carl.traingain;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import com.google.gson.JsonElement;
import com.microsoftopentechnologies.intellij.MobileService;
import com.microsoft.windowsazure.mobileservices.ApiJsonOperationCallback;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceJsonTable;
import com.microsoft.windowsazure.mobileservices.MobileServiceQuery;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableJsonQueryCallback;


import java.net.MalformedURLException;
/*
public class AzureServicesActivity extends Activity {

    /**
     * Initializes the activity
     */
  /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            //Obtain the MobileServiceClient object to query your mobile service
            MobileServiceClient mobileServiceClient = MobileService.getInstance(this);

            //Query or update your table's data using MobileServiceJsonTable
            MobileServiceJsonTable table_name = mobileServiceClient.getTable("TABLE_NAME");
            table_name.execute(new MobileServiceQuery<Object>(), new TableJsonQueryCallback() {
                @Override
                public void onCompleted(JsonElement jsonElement, int i, Exception e, ServiceFilterResponse serviceFilterResponse) {

                }
            });

            //Run your custom APIs
            mobileServiceClient.invokeApi("trial", new ApiJsonOperationCallback() {
                @Override
                public void onCompleted(JsonElement jsonElement, Exception e, ServiceFilterResponse serviceFilterResponse) {

                }
            });
        } catch (MalformedURLException e) {
            createAndShowDialog(e, "Error trying to get mobile service. Invalid URL");
        }
    }

    /**
     * Creates a dialog and shows it
     * 
     * @param exception
     *            The exception to show in the dialog
     * @param title
     *            The dialog title
     */

/*
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null){
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    /**
     * Creates a dialog and shows it
     * 
     * @param message
     *            The dialog message
     * @param title
     *            The dialog title
     */

  /*
    private void createAndShowDialog(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }
}
*/